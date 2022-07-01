package com.ihsanmkls.githubuserapp2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsanmkls.githubuserapp2.api.ApiConfig
import com.ihsanmkls.githubuserapp2.data.retrofit2.UserResponse
import com.ihsanmkls.githubuserapp2.data.retrofit2.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object{
        private const val TAG = "MainViewModel"
    }

    private val _listUsers = MutableLiveData<ArrayList<Users>>()
    val listUsers: LiveData<ArrayList<Users>> = _listUsers

    fun findUsers(data: String) {
        ApiConfig.getApiService().getSearchUsers(data).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    _listUsers.postValue(response.body()?.items)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}