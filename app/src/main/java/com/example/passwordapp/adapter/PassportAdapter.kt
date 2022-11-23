package com.example.passwordapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordapp.database.User
import com.example.passwordapp.databinding.ItemLayoutBinding

class PassportAdapter :
    RecyclerView.Adapter<PassportAdapter.PassportViewHolder>() {

    var passportList = mutableListOf<User>()
    lateinit var onItemClicked: (User) -> Unit
    lateinit var onDeleteClicked: (User, pos: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassportViewHolder {
        return PassportViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PassportViewHolder, position: Int) {
        holder.find(passportList[position])
    }

    override fun getItemCount(): Int = passportList.size

    inner class PassportViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun find(user: User) {
            binding.textName.text = "${adapterPosition.plus(1)}. ${user.lastName} ${user.name}"
            itemView.setOnClickListener {
                onItemClicked.invoke(user)
            }
            binding.btnMenu.setOnClickListener {
                onDeleteClicked(user, adapterPosition)
            }
        }

    }
    fun updateList(list: MutableList<User>){
        passportList = list
        notifyDataSetChanged()
    }
}