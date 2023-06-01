package com.demo.handlerdemo

import android.os.Looper

class DownloadThread(private val mActivity: MainActivity) : Thread() {

    lateinit var mHandler: DownloadHandler

    override fun run() {
        Looper.prepare()
        mHandler = DownloadHandler(mActivity, Looper.myLooper()!!)
        Looper.loop()
    }
}