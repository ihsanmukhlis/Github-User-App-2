package com.ihsanmkls.githubuserapp2.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsanmkls.githubuserapp2.data.database.FavoriteUsers
import com.ihsanmkls.githubuserapp2.data.database.FavoriteUsersRoomDatabase

class FavoriteViewModel : ViewModel() {

    private val _listUserFav = MutableLiveData<ArrayList<FavoriteUsers>>()
    private var listUserFav: LiveData<ArrayList<FavoriteUsers>> = _listUserFav

    fun getDataFavorite(context: Context): LiveData<ArrayList<FavoriteUsers>> {
        val database = FavoriteUsersRoomDatabase.getDatabase(context.applicationContext).favoriteUsersDao()
        _listUserFav.value = database.getAllFavorites() as ArrayList<FavoriteUsers>
        listUserFav = _listUserFav

        return listUserFav
    }
}