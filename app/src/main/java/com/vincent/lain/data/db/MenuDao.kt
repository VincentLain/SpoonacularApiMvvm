package com.vincent.lain.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.vincent.lain.data.model.Menu

@Dao
interface MenuDao {

    @Query("SELECT * FROM menu_table")
    fun getAll(): LiveData<List<Menu>>

    @Insert(onConflict = REPLACE)
    fun insert(menu: Menu)

    @Query("DELETE FROM menu_table WHERE id = :id")
    fun delete(id: Int?)

    @Query("DELETE FROM menu_table")
    fun deleteAll()
}