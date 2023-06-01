package com.demo.handlerdemo

import android.os.Handler
import android.os.Looper
import android.os.Message


class DownloadHandler(private val mActivity: MainActivity, looper: Looper) : Handler(looper) {

    override fun handleMessage(msg: Message) {
        val message = msg.obj.toString()
        downloadSong(message)
    }

    private fun downloadSong(song: String) {
        Thread.sleep(3000)
        mActivity.runOnUiThread {
            mActivity.log("Download Song $song")
            if (song == "Apsraa") {
                mActivity.displayProgressBar(false)
            }
        }
    }

}