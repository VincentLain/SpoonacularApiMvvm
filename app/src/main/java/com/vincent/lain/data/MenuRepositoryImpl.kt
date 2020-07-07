package com.vincent.lain.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vincent.lain.data.db.MenuDao
import com.vincent.lain.data.model.Menu
import com.vincent.lain.data.net.RetrofitClient
import com.vincent.lain.data.model.MenuResponse
import com.vincent.lain.db
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MenuRepositoryImpl : MenuRepository {

    private val menuDao : MenuDao = db.menuDao()
    private val retrofitClient = RetrofitClient()
    private val allMenus: LiveData<List<Menu>>

    init {
        allMenus = menuDao.getAll()
    }

    override fun deleteMenu(menu: Menu) {
        thread {
            db.menuDao().delete(menu.id)
        }
    }

    override fun getSaveMenus() = allMenus

    override fun saveMenu(menu: Menu) {
        thread {
            menuDao.insert(menu)
        }
    }

    override fun searchMenu(query: String): LiveData<List<Menu>?> {
        val data = MutableLiveData<List<Menu>>()

        retrofitClient.searchMenus(query).enqueue(object : Callback<MenuResponse> {
            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                data.value = null
                Log.d(this.javaClass.simpleName, "Failure")
            }

            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                data.value = response.body()?.menuItems
                Log.d(this.javaClass.simpleName, "Response: ${response.body()?.menuItems}")
            }
        })
        return data
    }
}