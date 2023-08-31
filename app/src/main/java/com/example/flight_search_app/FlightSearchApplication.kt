package com.example.flight_search_app

import android.app.Application

class FlightSearchApplication: Application() {

    lateinit var container: Container

    override fun onCreate() {
        super.onCreate()
        container = ContainerImpl(this)
    }
}