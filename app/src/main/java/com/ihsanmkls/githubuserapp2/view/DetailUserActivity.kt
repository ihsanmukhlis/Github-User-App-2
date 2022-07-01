package com.ihsanmkls.githubuserapp2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ihsanmkls.githubuserapp2.R
import com.ihsanmkls.githubuserapp2.adapter.SectionPagerAdapter
import com.ihsanmkls.githubuserapp2.data.database.FavoriteUsers
import com.ihsanmkls.githubuserapp2.data.database.FavoriteUsersDao
import com.ihsanmkls.githubuserapp2.data.database.FavoriteUsersRoomDatabase
import com.ihsanmkls.githubuserapp2.databinding.ActivityDetailUserBinding
import com.ihsanmkls.githubuserapp2.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var databaseDao: FavoriteUsersDao
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    private var user : String = ""
    private var name : String = ""
    private var avatar : String = ""
    private var githubUrl : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getStringExtra(EXTRA_USER).toString()

        databaseDao = FavoriteUsersRoomDatabase.getDatabase(applicationContext).favoriteUsersDao()
        val exist = databaseDao.getFavoriteExists(user)
        setFavoriteIcon(exist)

        detailUserViewModel.userDetail(user)
        detailUserViewModel.user.observe(this) {
            if (it != null) {
                avatar = it.avatar_url.toString()
                name = it.name.toString()
                githubUrl = it.html_url.toString()
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgDetailAvatar)
                    tvDetailName.text = name
                    tvDetailUsername.text = it.login
                    tvDetailCompany.text = it.company
                    tvDetailLocation.text = it.location
                    tvDetailFollowers.text = it.followers.toString()
                    tvDetailFollowing.text = it.following.toString()
                    tvDetailRepository.text = it.public_repos.toString()

                    showLoading(false)
                }
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.favoriteButton.setOnClickListener(this)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.favoriteButton -> {
                onSetFavoriteIcon(databaseDao.getFavoriteExists(user), view)
            }
        }
    }

    private fun setFavoriteIcon(exists: Boolean) {
        if (exists) {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_favorite_exists
                )
            )
        } else {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_favorite_white
                )
            )
        }
    }

    private fun onSetFavoriteIcon(exists: Boolean, view: View) {
        if (!exists) {
            val insertData = FavoriteUsers(
                name = name,
                username = user,
                avatar = avatar,
                github_url = githubUrl
            )
            databaseDao.insert(insertData)
            Snackbar.make(view, "$user ${getString(R.string.msg_success_add_fav)}", Snackbar.LENGTH_SHORT).show()
            setFavoriteIcon(true)
        } else {
            databaseDao.delete(user)
            Snackbar.make(view, "$user ${getString(R.string.msg_success_delete_fav)}", Snackbar.LENGTH_SHORT).show()
            setFavoriteIcon(false)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}