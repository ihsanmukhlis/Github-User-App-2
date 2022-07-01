package com.ihsanmkls.githubuserapp2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsanmkls.githubuserapp2.adapter.FavoritesAdapter
import com.ihsanmkls.githubuserapp2.data.database.FavoriteUsers
import com.ihsanmkls.githubuserapp2.databinding.ActivityFavoriteBinding
import com.ihsanmkls.githubuserapp2.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoritesAdapter
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showLoading(true)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setData(data: ArrayList<FavoriteUsers>) {
        adapter = FavoritesAdapter(data, this)
        binding.rvUsersFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvUsersFavorite .adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getDataFavorite(this).observe(this) { listUsers ->
            if (listUsers != null) {
                setData(listUsers)
                showLoading(false)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}