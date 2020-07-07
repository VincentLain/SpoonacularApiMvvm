package com.vincent.lain.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.vincent.lain.data.MenuRepository
import com.vincent.lain.data.MenuRepositoryImpl
import com.vincent.lain.data.model.Menu

class MainViewModel(private val repository: MenuRepository = MenuRepositoryImpl()) : ViewModel() {

    private val allMenus = MediatorLiveData<List<Menu>>()

    init {
        getAllMenus()
    }

    fun getSavedMenus() = allMenus

    private fun getAllMenus() {
        allMenus.addSource(repository.getSaveMenus()) { menus ->
                        allMenus.postValue(menus)
        }
    }

    fun deleteSavedMenus(menu: Menu) {
        repository.deleteMenu(menu)
    }

}