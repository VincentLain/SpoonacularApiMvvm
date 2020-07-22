package com.vincent.lain.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.vincent.lain.data.model.Menu
import com.vincent.lain.data.net.RetrofitClient

class MenuDataSourceFactory(
    private val apiService: RetrofitClient,
    private val query: String
) : DataSource.Factory<Int, Menu>() {

    val menuDataSource = MutableLiveData<MenuDataSource>()

    override fun create(): DataSource<Int, Menu> {
        val source = MenuDataSource(apiService, query)
        menuDataSource.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 10

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}