package com.eterocell.samples

import android.app.Application

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        StrictMode.setThreadPolicy(
//            StrictMode.ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyDialog()
//                .build(),
//        )
//
//        StrictMode.setVmPolicy(
//            StrictMode.VmPolicy.Builder()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .build(),
//        )
    }
}
