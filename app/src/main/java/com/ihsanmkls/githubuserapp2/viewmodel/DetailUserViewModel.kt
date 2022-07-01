package com.ihsanmkls.githubuserapp2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsanmkls.githubuserapp2.api.ApiConfig
import com.ihsanmkls.githubuserapp2.data.retrofit2.DetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    companion object{
        private const val TAG = "DetailUserViewModel"
    }

    private val _user = MutableLiveData<DetailUser>()
    val user: LiveData<DetailUser> = _user

    fun userDetail(data: String) {
        ApiConfig.getApiService().getUserDetail(data).enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: Response<DetailUser>
            ) {
                if (response.isSuccessful) {
                    _user.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}