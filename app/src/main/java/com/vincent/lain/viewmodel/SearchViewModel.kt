package com.vincent.lain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vincent.lain.data.MenuRepository
import com.vincent.lain.data.MenuRepositoryImpl
import com.vincent.lain.data.model.Menu

class SearchViewModel(private val repository: MenuRepository = MenuRepositoryImpl()) : ViewModel() {

    fun searchMenu(query: String): LiveData<List<Menu>?> {
        return repository.searchMenu(query)
    }

    fun saveMenu(menu: Menu) {
        repository.saveMenu(menu)
    }
}