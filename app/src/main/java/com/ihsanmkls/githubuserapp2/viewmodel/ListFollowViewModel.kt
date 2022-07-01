package com.ihsanmkls.githubuserapp2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsanmkls.githubuserapp2.api.ApiConfig
import com.ihsanmkls.githubuserapp2.data.retrofit2.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFollowViewModel : ViewModel() {

    private val _listFollow = MutableLiveData<ArrayList<Users>>()
    val listFollow: LiveData<ArrayList<Users>> = _listFollow

    fun followersDetail(data: String) {
        ApiConfig.getApiService().getUserFollowers(data).enqueue(object :
            Callback<ArrayList<Users>> {
            override fun onResponse(
                call: Call<ArrayList<Users>>,
                response: Response<ArrayList<Users>>
            ) {
                if (response.isSuccessful) {
                    _listFollow.postValue(response.body())
                } else {
                    Log.e(TAG_FOLLOWERS, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                Log.d(TAG_FOLLOWERS, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun followingDetail(data: String) {
        ApiConfig.getApiService().getUserFollowing(data).enqueue(object :
            Callback<ArrayList<Users>> {
            override fun onResponse(
                call: Call<ArrayList<Users>>,
                response: Response<ArrayList<Users>>
            ) {
                if (response.isSuccessful) {
                    _listFollow.postValue(response.body())
                } else {
                    Log.e(TAG_FOLLOWING, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                Log.d(TAG_FOLLOWING, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG_FOLLOWERS = "FollowersViewModel"
        private const val TAG_FOLLOWING = "FollowingViewModel"
    }
}