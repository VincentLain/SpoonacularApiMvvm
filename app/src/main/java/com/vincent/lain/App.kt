package com.vincent.lain

import android.app.Application
import com.vincent.lain.data.db.MenuDatabase
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

lateinit var db: MenuDatabase

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        db = MenuDatabase.getInstance(this)
        INSTANCE = this
        this.initCicerone()
    }

    private fun initCicerone() {
        this.cicerone = Cicerone.create()
    }
}