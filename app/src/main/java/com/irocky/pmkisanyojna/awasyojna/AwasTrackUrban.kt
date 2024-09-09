package com.irocky.pmkisanyojna.awasyojna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irocky.pmkisanyojna.R

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class AwasTrackUrban : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    lateinit var mAdView: AdView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awas_track_urban)

        supportActionBar?.apply {
            title = "Awas Track Urban"
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(R.color.teal_200))
        }

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        webView = findViewById(R.id.web_view_aws)
        progressBar = findViewById(R.id.progress_bar_news)

        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.setSupportZoom(true)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
                webView.settings.textZoom = 100
            }

            @Deprecated("Deprecated in Java")
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

        webView.loadUrl("https://pmaymis.gov.in/open/check_aadhar_existence.aspx")
    }

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

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBackOrForward(-webView.copyBackForwardList().currentIndex)
        } else {
            super.onBackPressed()
        }
    }
}
