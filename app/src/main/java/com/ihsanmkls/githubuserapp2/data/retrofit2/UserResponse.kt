package com.ihsanmkls.githubuserapp2.data.retrofit2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    var items: ArrayList<Users>
) : Parcelable