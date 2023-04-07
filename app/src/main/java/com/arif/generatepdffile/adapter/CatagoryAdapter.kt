package com.arif.generatepdffile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arif.generatepdffile.databinding.DesignLayoutBinding
import com.arif.generatepdffile.model.CategoriesModel

class CatagoryAdapter():ListAdapter<CategoriesModel.Category,CatagoryAdapter.UserViewHolder>(UserDiffutil()) {

    class UserViewHolder(val binding:DesignLayoutBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(item:CategoriesModel.Category){
                    binding.item = item
                }
            }

    class UserDiffutil:DiffUtil.ItemCallback<CategoriesModel.Category>(){
        override fun areItemsTheSame(
            oldItem: CategoriesModel.Category,
            newItem: CategoriesModel.Category
        ): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(
            oldItem: CategoriesModel.Category,
            newItem: CategoriesModel.Category
        ): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val binding = DesignLayoutBinding.inflate(
           LayoutInflater.from(parent.context),parent,false
       )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.textId.setOnClickListener {

        }
    }
}