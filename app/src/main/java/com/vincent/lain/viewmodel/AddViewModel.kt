package com.vincent.lain.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vincent.lain.data.MenuRepository
import com.vincent.lain.data.MenuRepositoryImpl
import com.vincent.lain.data.model.Menu

class AddViewModel(private val repository: MenuRepository = MenuRepositoryImpl()) : ViewModel() {

    var title = ObservableField<String>("")

    private val saveLiveData = MutableLiveData<Boolean>()

    fun getSaveLiveData() : LiveData<Boolean> = saveLiveData

    fun saveMenu() {
        if (canSaveMenu()) {
            repository.saveMenu(Menu(title = title.get()))
            saveLiveData.postValue(true)
        } else {
            saveLiveData.postValue(false)
        }
    }

    fun canSaveMenu() : Boolean {
        val title = this.title.get()
        if (title != null) {
            return title.isNotEmpty()
        }
        return false
    }
}