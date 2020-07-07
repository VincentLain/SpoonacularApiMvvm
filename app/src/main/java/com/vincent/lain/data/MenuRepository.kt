package com.vincent.lain.data

import androidx.lifecycle.LiveData
import com.vincent.lain.data.model.Menu

interface MenuRepository {
    fun getSaveMenus() : LiveData<List<Menu>>

    fun saveMenu(menu : Menu)

    fun deleteMenu(menu: Menu)

    fun searchMenu(query: String): LiveData<List<Menu>?>
}