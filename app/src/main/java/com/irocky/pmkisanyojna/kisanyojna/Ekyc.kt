package com.irocky.pmkisanyojna.kisanyojna

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.webkit.DownloadListener
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.irocky.pmkisanyojna.R

class Ekyc : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    lateinit var mAdView: AdView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ekyc)

        supportActionBar?.apply {
            title = "PM KIsan Yojna eKYC"
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(R.color.teal_200))
        }

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        webView = findViewById(R.id.webb_view)
        progressBar = findViewById(R.id.progresss_bar)

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
                webView.settings.textZoom = 210

//                webView.evaluateJavascript(
//                    """
//            // Hide the Header element
//            var headerElement = document.getElementById("Header");
//            if (headerElement != null) {
//                headerElement.style.display = "none";
//            }
//
//            // Hide the inner-left-div element
//            var innerLeftDiv = document.getElementById("inner-left-div");
//            if (innerLeftDiv != null) {
//                innerLeftDiv.style.display = "none";
//            }
//
//             // Hide the logo-div element
//            var logoDiv = document.getElementById("logo-div");
//            if (logoDiv != null) {
//                logoDiv.style.display = "none";
//            }
//
//             // Hide the inner-right-link element
//            var innerRightLink = document.getElementsByClassName("inner-right-link")[0];
//            if (innerRightLink != null) {
//                innerRightLink.style.display = "none";
//            }
//
//              // Hide the footer-bg element
//            var footerBg = document.getElementById("footer-bg");
//            if (footerBg != null) {
//                footerBg.style.display = "none";
//            }
//
//            """,
//                    null
//                )
            }

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

        webView.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val fileName = URLUtil.guessFileName(url, contentDisposition, mimetype)
            val downloadUri = Uri.parse(url)
            val request = DownloadManager.Request(downloadUri)
            request.setMimeType(mimetype)
            request.setTitle(fileName)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            try {
                val downloadId = downloadManager.enqueue(request)
                showToast("Download started")
            } catch (e: IllegalArgumentException) {
                // Handle exception if download manager is disabled
            }
        })

        webView.loadUrl("https://exlink.pmkisan.gov.in/aadharekyc.aspx")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}


