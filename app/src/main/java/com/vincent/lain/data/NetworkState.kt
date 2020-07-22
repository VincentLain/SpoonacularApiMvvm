package com.vincent.lain.data

enum class Status {RUNNING, SUCCESS, FAILED}

class NetworkState(private val status: Status, private val msg: String) {

    companion object {
        val LOADED: NetworkState? =
            NetworkState(
                Status.SUCCESS,
                "Success"
            )
        val LOADING: NetworkState? =
            NetworkState(
                Status.RUNNING,
                "Running"
            )
    }

    fun getStatus(): Status {
        return status
    }

    fun getMsg(): String {
        return msg
    }



}