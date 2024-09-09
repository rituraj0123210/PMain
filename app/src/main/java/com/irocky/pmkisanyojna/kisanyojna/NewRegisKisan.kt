package com.irocky.pmkisanyojna.kisanyojna

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.irocky.pmkisanyojna.R

class NewRegisKisan : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    lateinit var mAdView: AdView


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_regis)

        supportActionBar?.apply {
            title = "PM KIsan Yojna Registration"
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(R.color.teal_200))
        }

        MobileAds.initialize(this,) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        webView = findViewById(R.id.webView_parchi)
        progressBar = findViewById(R.id.progresss_bar_parchi)

        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.setSupportZoom(true)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

//        webView.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                progressBar.visibility = View.GONE
//                webView.visibility = View.VISIBLE

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Show the progress bar and start the animation
                progressBar.visibility = View.VISIBLE
                startAnimation()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Hide the progress bar and stop the animation
                progressBar.visibility = View.GONE
                stopAnimation()
                webView.visibility = View.VISIBLE
            }

//                webView.loadUrl("javascript:(function() { " +
//                        "var elementUtype = document.getElementById('Utype');" +
//                        "if (elementUtype) {" +
//                        "    elementUtype.parentNode.removeChild(elementUtype);" +
//                        "}" +
//                        "var elementsP = document.getElementsByTagName('p');" +
//                        "for (var i = 0; i < elementsP.length; i++) {" +
//                        "    var element = elementsP[i];" +
//                        "    if (element.getAttribute('style') === 'text-align:left') {" +
//                        "        element.parentNode.removeChild(element);" +
//                        "    }" +
//                        "}" +
//                        "})()")




            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                val uri = Uri.parse(url)
                if (uri.scheme == "http" || uri.scheme == "https") {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                return true
            }
        }

        webView.loadUrl("https://pmkisan.gov.in/RegistrationFormNew.aspx")
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            onBackPressed()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_khatauni, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                webView.reload()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.progress_bar_animation)
        progressBar.startAnimation(animation)
    }

    private fun stopAnimation() {
        progressBar.clearAnimation()
    }
//
//    override fun onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack()
//        } else {
//            super.onBackPressed()
//        }
//    }
}
