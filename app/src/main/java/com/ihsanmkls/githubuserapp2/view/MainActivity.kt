package com.ihsanmkls.githubuserapp2.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsanmkls.githubuserapp2.R
import com.ihsanmkls.githubuserapp2.adapter.UsersAdapter
import com.ihsanmkls.githubuserapp2.data.SettingPreferences
import com.ihsanmkls.githubuserapp2.data.retrofit2.Users
import com.ihsanmkls.githubuserapp2.databinding.ActivityMainBinding
import com.ihsanmkls.githubuserapp2.viewmodel.MainViewModel
import com.ihsanmkls.githubuserapp2.viewmodel.SettingViewModel
import com.ihsanmkls.githubuserapp2.viewmodel.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val usersAdapter = UsersAdapter()
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUsers.observe(this) {
            if (it != null)
                showLoading(false)
                onDataNotDisplayed(false)
                usersAdapter.setDataUser(it)
        }

        onDataNotDisplayed(true)
        usersAdapter.notifyDataSetChanged()
        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickedCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = usersAdapter

            btnSearch.setOnClickListener {
                onFindUsers()
            }

            inputText.setOnKeyListener{ _, key, event ->
                if (event.action == KeyEvent.ACTION_DOWN && key == KeyEvent.KEYCODE_ENTER) {
                    onFindUsers()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                val intentSetting = Intent(this, SettingActivity::class.java)
                startActivity(intentSetting)
            }
            R.id.action_favorite -> {
                val intentSetting = Intent(this, FavoriteActivity::class.java)
                startActivity(intentSetting)
            }
        }
        return true
    }

    private fun onFindUsers() {
        binding.apply {
            val search = inputText.text.toString()

            if (search.isEmpty()) return
            showLoading(true)
            onDataNotDisplayed(false)
            mainViewModel.findUsers(search)
        }
    }

    private fun onDataNotDisplayed(isNotDisplayed: Boolean) { binding.tvNotDisplayed.visibility = if (isNotDisplayed) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}