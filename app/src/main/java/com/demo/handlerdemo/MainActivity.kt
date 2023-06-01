package com.demo.handlerdemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val tag = "MyTag"
    private lateinit var mScroll: ScrollView
    private lateinit var mLog: TextView
    private lateinit var mProgressBar: ProgressBar

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private lateinit var mDownloadThread: DownloadThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        mDownloadThread = DownloadThread(this@MainActivity)
        mDownloadThread.name = "Background Thread"
        mDownloadThread.start()
    }

    private fun initViews() {
        mScroll = findViewById<View>(R.id.scrollLog) as ScrollView
        mLog = findViewById<View>(R.id.tvLog) as TextView
        mProgressBar = findViewById<View>(R.id.progress_bar) as ProgressBar
    }

    fun runCode(v: View) {
        log("Running code")
        displayProgressBar(true)

        /*
        There are two main uses for a Handler:
        (1) to schedule messages and runnable to be executed at some point in the future;
        */

        /*mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            log("Task completed")

            displayProgressBar(false)
        }, 5000)*/

        /*
        (2) to enqueue an action to be performed on a different thread than your own.
        */
        //performActionUsingHandler()


        for (song in Playlist.songs) {
            val message = Message.obtain()
            message.obj = song
            mDownloadThread.mHandler.sendMessage(message)
        }
    }

    private fun performActionUsingHandler() {
        mHandler = object : Handler(mainLooper) {
            override fun handleMessage(msg: Message) {
                log("Task completed")
                displayProgressBar(false)
            }
        }

        mRunnable = Runnable {
            log("Task Running")
            Thread.sleep(5000)

            val msg = Message()
            mHandler.sendMessage(msg)
        }
        val thread = Thread(mRunnable)
        thread.start()
    }

    fun clearOutput(v: View) {
        mLog.text = ""
        scrollTextToEnd()
    }

    fun log(message: String) {
        Log.i(tag, message + " and Current Thread : " + Thread.currentThread().name)
        mLog.append("$message \n")
        scrollTextToEnd()
    }

    private fun scrollTextToEnd() {
        mScroll.post { mScroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    fun displayProgressBar(display: Boolean) {
        if (display) {
            mProgressBar.visibility = View.VISIBLE
        } else {
            mProgressBar.visibility = View.INVISIBLE
        }
    }
}