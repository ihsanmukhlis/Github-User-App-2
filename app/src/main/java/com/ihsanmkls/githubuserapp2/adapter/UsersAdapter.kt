package com.ihsanmkls.githubuserapp2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ihsanmkls.githubuserapp2.data.retrofit2.Users
import com.ihsanmkls.githubuserapp2.databinding.ItemListUsersBinding

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

    private val listUsers = ArrayList<Users>()

    private var onItemClickedCallback: OnItemClickedCallback? = null

    inner class ListViewHolder(private val binding: ItemListUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.apply {
                Glide.with(itemView)
                    .load(users.avatar_url)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgUserAvatar)
                tvUsername.text = users.login
                tvGithubUrl.text = users.html_url
            }

            binding.root.setOnClickListener {
                onItemClickedCallback?.onItemClicked(users)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder((view))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size

    fun setDataUser(data: ArrayList<Users>) {
        listUsers.clear()
        listUsers.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickedCallback: OnItemClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback
    }

    interface OnItemClickedCallback {
        fun onItemClicked(data: Users)
    }
}