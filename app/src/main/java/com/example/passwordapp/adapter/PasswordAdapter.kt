package com.example.passwordapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordapp.database.User
import com.example.passwordapp.databinding.ItemLayoutBinding

class PasswordAdapter :
    RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {
    var passwordList = mutableListOf<User>()
    lateinit var onItemClicked: (User) -> Unit
    lateinit var onDeleteClicked: (User, pos: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        return PasswordViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.bind(passwordList[position])
    }

    override fun getItemCount(): Int = passwordList.size

    inner class PasswordViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textName.text = "${adapterPosition.plus(1)}. ${user.lastName} ${user.name}"
            itemView.setOnClickListener {
                onItemClicked.invoke(user)
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClicked(user, adapterPosition)
            }
        }
    }

    fun updateList(list: MutableList<User>) {
        passwordList = list
        notifyDataSetChanged()
    }
}