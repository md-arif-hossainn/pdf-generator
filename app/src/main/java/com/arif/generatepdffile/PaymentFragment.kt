package com.arif.generatepdffile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.arif.generatepdffile.databinding.FragmentPaymentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*


class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    lateinit var client: FusedLocationProviderClient
    var fusedlocation = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater,container,false)
        client = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()
        val paymentType = arguments?.getString("Type")
        if (paymentType == "Bkash") {
            binding.icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_bkash
                )
            )
            binding.numberTxt.text = "Bkash number"
        } else {
            binding.icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_nagad
                )
            )
            binding.numberTxt.text = "Nagad number"
        }

        binding.submitBtn.setOnClickListener {
            val number = binding.numberEt.text.toString()
            val name = binding.fullNameEt.text.toString()
            val narration = binding.narrationET.text.toString()
            val amount = binding.amountEt.text.toString()

            if (checkValidation(number, name, amount, paymentType)) {
                showAlertDialog(number, name, amount, narration, paymentType)
            }
        }
        return binding.root
    }

    private fun checkValidation(
        number: String,
        name: String,
        amount: String,
        paymentType: String?
    ): Boolean {
        if (number.isEmpty()) {
            binding.numberEt.error = "Enter $paymentType number"
            binding.numberEt.hasFocus()
            return false
        } else if (name.isEmpty()) {
            binding.fullNameEt.error = "Please,enter name"
            return false
        } else if (amount.isEmpty()) {
            binding.amountEt.error = "Please,enter amount"
            return false
        }
        else if (amount.toDouble()<=0){
            binding.amountEt.error = "Please,enter amount"
            return false
        }
        return true
    }

    private fun showAlertDialog(
        number: String,
        name: String,
        amount: String,
        narration: String,
        paymentType: String?
    ) {

        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.status_dialog, null)

        val dismiss = dialogLayout.findViewById<MaterialCardView>(R.id.dissmiss)
        val icon = dialogLayout.findViewById<ImageView>(R.id.imageView)
        val fundTransferTxt = dialogLayout.findViewById<TextView>(R.id.fundTransferTxt)
        val amountTv = dialogLayout.findViewById<TextView>(R.id.amount)
        val transactionTime = dialogLayout.findViewById<TextView>(R.id.time)
        val location = dialogLayout.findViewById<TextView>(R.id.location)
        val narrationTv = dialogLayout.findViewById<TextView>(R.id.narration)
        val numberTv = dialogLayout.findViewById<TextView>(R.id.number)
        val numbertxt = dialogLayout.findViewById<TextView>(R.id.paymentTypeNumberTxt)
        val totalTv = dialogLayout.findViewById<TextView>(R.id.totalAmount)
        val downloadReceipt = dialogLayout.findViewById<MaterialButton>(R.id.downloadPdf)
        val shareReceipt = dialogLayout.findViewById<MaterialButton>(R.id.sharePdf)



        if (paymentType == "Bkash") {
            icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_bkash
                )
            )
        } else {
            icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_nagad
                )
            )
        }
        fundTransferTxt.text = "$paymentType Fund Transfer"
        amountTv.text = amount.toDouble().toString()
        transactionTime.text = setTime()
        totalTv.text = "BDT $amount"
        numberTv.text = number
        numbertxt.text = "$paymentType number"
        narrationTv.text = narration
        location.text = fusedlocation

        val pdfData = PdfData(
            name,
            amount,
            number,
            narration,
            paymentType.toString(),
            fusedlocation,
            transactionTime.text.toString(),
            true
        )
        val pdfConverter = PDFConverter()

        downloadReceipt.setOnClickListener {
            pdfConverter.createPdf(requireContext(), pdfData, requireActivity())
        }


        shareReceipt.setOnClickListener {
            val pdfConverterfORsHARE = PDFConverter()
            val pdfDataForShare = PdfData(
                name,
                amount,
                number,
                narration,
                paymentType.toString(),
                fusedlocation,
                transactionTime.text.toString(),
                false
            )

            pdfConverterfORsHARE.createPdf(requireContext(), pdfDataForShare, requireActivity())

        }


        builder.setView(dialogLayout)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        dismiss.setOnClickListener {
            dialog.dismiss()
            Navigation.findNavController(requireView())
                .navigate(R.id.action_paymentFragment_to_dashboardFragment)
        }

    }
    private fun setTime(): CharSequence? {
        val simpleDate = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        return simpleDate.format(Date())

    }

    private fun getCurrentLocation() {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            val priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            val cancellationTokenSource = CancellationTokenSource()

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            client.getCurrentLocation(priority, cancellationTokenSource.token)
                .addOnSuccessListener { location ->
                    Log.d("Location", "location is found: $location")
                    Log.e("Location", "getCurrentLocation: ${location.latitude}")

                    if (location != null) {
                        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
                        val address =
                            geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                        fusedlocation = address?.get(0)?.adminArea.toString()
                        Log.e("Location", "getCurrentLocation: 2 $fusedlocation")
                    } else {
                        client.lastLocation.addOnSuccessListener {
                            if (it != null) {
                                val geoCoder = Geocoder(requireContext(), Locale.getDefault())
                                val address = geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                                fusedlocation = address?.get(0)?.adminArea.toString()

                                Log.e("Location", "getCurrentLocation: 1 $fusedlocation")
                            }
                        }
                    }


                }
                .addOnFailureListener { exception ->
                    Log.d("Location", "Oops location failed with exception: $exception")
                }
        }
    }
}