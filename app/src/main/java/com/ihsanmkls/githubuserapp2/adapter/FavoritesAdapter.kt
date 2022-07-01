package com.ihsanmkls.githubuserapp2.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ihsanmkls.githubuserapp2.data.database.FavoriteUsers
import com.ihsanmkls.githubuserapp2.databinding.ItemListUsersBinding
import com.ihsanmkls.githubuserapp2.view.DetailUserActivity

class FavoritesAdapter(private val listUserFav: ArrayList<FavoriteUsers>, private val activity: Activity) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemListUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteUsers) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.avatar)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgUserAvatar)
                tvUsername.text = data.username
                tvGithubUrl.text = data.github_url
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, data.username.toString())
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listUserFav.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUserFav[position])
    }
}