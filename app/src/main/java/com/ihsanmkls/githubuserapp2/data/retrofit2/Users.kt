package com.ihsanmkls.githubuserapp2.data.retrofit2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var login: String?,
    var html_url: String?,
    var avatar_url: String?
) : Parcelable
