package com.ihsanmkls.githubuserapp2.data.database

import androidx.room.*

@Dao
interface FavoriteUsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUsers)

    @Query("DELETE FROM FavoriteUsers WHERE username= :username")
    fun delete(username: String)

    @Query("SELECT * FROM FavoriteUsers ORDER BY id ASC")
    fun getAllFavorites(): List<FavoriteUsers>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUsers WHERE username= :username)")
    fun getFavoriteExists(username: String): Boolean
}