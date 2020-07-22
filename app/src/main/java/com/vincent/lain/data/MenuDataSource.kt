package com.vincent.lain.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.vincent.lain.data.model.Menu
import com.vincent.lain.data.model.MenuResponse
import com.vincent.lain.data.net.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuDataSource(
    var apiService: RetrofitClient,
    var query: String
) : PageKeyedDataSource<Int, Menu>() {

    private val TAG: String = MenuDataSource::class.java.simpleName

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Menu>
    ) {
        networkState.postValue(NetworkState.LOADING)
        fetchData(query, 0, 10) {
            callback.onResult(it, null, 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Menu>) {
        networkState.postValue(NetworkState.LOADING)
        val page = params.key
        fetchData(query, page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Menu>) {
        val page = params.key
        networkState.postValue(NetworkState.LOADING)
        fetchData(query, page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(query: String, page: Int, pageSize: Int, callback: (List<Menu>) -> Unit) {

        Log.d(this.javaClass.simpleName, "fetchData")
        Log.d(this.javaClass.simpleName, "query $query")
        apiService.searchMenus(query, page, pageSize).enqueue(object : Callback<MenuResponse> {
            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                Log.d(this.javaClass.simpleName, "Failure")
                var errorMessage = t.message
                if (t == null) errorMessage = "unknown error"
                networkState.postValue(errorMessage?.let {
                    NetworkState(
                        Status.FAILED,
                        it
                    )
                })
            }

            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                Log.d(this.javaClass.simpleName, "onResponse")
                if (response.isSuccessful && response.code() == 200) {
                    networkState.postValue(NetworkState.LOADED)
                    val remoteData = response.body()?.menuItems
                    if (remoteData != null) {
                        callback(remoteData)
                    }
                    Log.d(this.javaClass.simpleName, "total: ${response.body()?.totalMenuItems}")
                    Log.d(this.javaClass.simpleName, "number: ${response.body()?.number}")
                    Log.d(this.javaClass.simpleName, "offset: ${response.body()?.offset}")
                    Log.d(this.javaClass.simpleName, "Response: ${response.body()?.menuItems}")
                } else {
                    Log.e("API CALL", response.message())
                    networkState.postValue(
                        NetworkState(
                            Status.FAILED,
                            response.message() + response.code().toString()
                        )
                    )
                }
            }

        })
    }


}