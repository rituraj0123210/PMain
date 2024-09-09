package com.irocky.pmkisanyojna

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.IOException
import java.io.InputStream

class AboutActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_about)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "About"
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(R.color.teal_200))
        }


        webView = findViewById(R.id.webViewAbout)
        webView.webViewClient = WebViewClient()

        // Load the HTML content from the assets folder
        val htmlContent = loadHtmlFromAssets("about.html")
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        // Handle back button press
        // Note: You might need to add additional logic to handle the back button as per your requirements.
        // This is a simple example of finishing the activity when the back button is pressed.
    }

    // Method to load HTML content from assets folder
    private fun loadHtmlFromAssets(fileName: String): String {
        try {
            val inputStream: InputStream = assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
