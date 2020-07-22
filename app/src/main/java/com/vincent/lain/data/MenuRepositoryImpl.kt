package com.vincent.lain.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.vincent.lain.data.db.MenuDao
import com.vincent.lain.data.model.Menu
import com.vincent.lain.data.net.RetrofitClient
import com.vincent.lain.db
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

    override fun searchMenu(query: String): Listing<Menu> {
        val menuDataSourceFactory = MenuDataSourceFactory(retrofitClient, query)
        val livePagedList = LivePagedListBuilder(menuDataSourceFactory, MenuDataSourceFactory.pagedListConfig()).build()

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(menuDataSourceFactory.menuDataSource) {
                it.networkState
            }
        )

    }





}