package com.example.newsapp

import android.app.DownloadManager
import android.content.Context
import android.provider.CalendarContract
import com.android.volley.Request
import com.android.volley.toolbox.Volley

class mySingleton constructor(context:Context) {

    companion object{
        private var INSTANCE:mySingleton?=null
        fun getInstance(context: Context)=
            INSTANCE?: synchronized(this){
            INSTANCE?:mySingleton(context).also {
                INSTANCE=it
            }
        }
    }
    private val requestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}