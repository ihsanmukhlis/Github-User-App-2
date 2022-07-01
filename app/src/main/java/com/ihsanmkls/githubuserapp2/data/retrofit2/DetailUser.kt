package com.ihsanmkls.githubuserapp2.data.retrofit2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser (
    var name: String?,
    var login: String?,
    var html_url: String?,
    var avatar_url: String?,
    var followers: Long?,
    var following: Long?,
    var public_repos: Long?,
    var location: String?,
    var company: String?
) : Parcelable