package com.vincent.lain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vincent.lain.data.Listing
import com.vincent.lain.data.MenuRepository
import com.vincent.lain.data.MenuRepositoryImpl
import com.vincent.lain.data.model.Menu


class SearchViewModel(
    query: String,
    private val repository: MenuRepository = MenuRepositoryImpl()
) : ViewModel() {
    private val menuList = MutableLiveData<Listing<Menu>>()
    val menus = Transformations.switchMap(menuList) { it.pagedList }
    val networkState = Transformations.switchMap(menuList) { it.networkState }

    init {
        menuList.value = repository.searchMenu(query)
    }

    fun saveMenu(menu: Menu) {
        repository.saveMenu(menu)
    }
}