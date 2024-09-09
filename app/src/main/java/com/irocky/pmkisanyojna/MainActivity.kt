package com.irocky.pmkisanyojna

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.*

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.denzcoskun.imageslider.ImageSlider
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.navigation.NavigationView
import java.util.*

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.*
import com.irocky.pmkisanyojna.awasyojna.AwasYojna
import com.irocky.pmkisanyojna.kisanyojna.NewRegisKisan
import com.irocky.pmkisanyojna.kisanyojna.Ekyc
import com.irocky.pmkisanyojna.kisanyojna.KisanYojna


class MainActivity : AppCompatActivity() {

    private lateinit var mainslider: ImageSlider
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference
    private val childref = databaseReference.child("URL")

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var noInternetText: ImageView


    lateinit var wishM: TextView
    lateinit var mAdView: AdView
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


//        noInternetText = findViewById(R.id.noInternetText)
//
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkCallback = object : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network) {
//                runOnUiThread {
//                    noInternetText.visibility = View.GONE
//                }
//            }
//
//            override fun onLost(network: Network) {
//                runOnUiThread {
//                    noInternetText.visibility = View.VISIBLE
//                }
//            }
//        }
//
//        val networkRequest = NetworkRequest.Builder().build()
//        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)






        mainslider = findViewById(R.id.image_slider)
        val remoteimages: MutableList<SlideModelWithUrl> = ArrayList()

        loadInterstitialAd()

        FirebaseDatabase.getInstance().reference.child("Slider")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (data in dataSnapshot.children) {
                        val imageUrl = data.child("url").value.toString()
                        val imageTitle = data.child("title").value.toString()
                        val websiteUrl = data.child("websiteUrl").value.toString()
                        val activityName = data.child("activityName").value.toString()

                        val slideModel = SlideModel(imageUrl, imageTitle, ScaleTypes.FIT)
                        val slideModelWithUrl = SlideModelWithUrl(slideModel, websiteUrl, activityName)
                        remoteimages.add(slideModelWithUrl)
                    }

                    mainslider.setImageList(remoteimages.map { it.slideModel }, ScaleTypes.FIT)
                    mainslider.setItemClickListener(object : ItemClickListener {
                        override fun doubleClick(position: Int) {
                            // Double click action
                        }

//                        override fun onItemSelected(i: Int) {
//                            val websiteUrl = remoteimages[i].websiteUrl
//                            if (websiteUrl.isNotEmpty()) {
//                                val intent = Intent(Intent.ACTION_VIEW)
//                                intent.data = Uri.parse(websiteUrl)
//                                startActivity(intent)
//                            }
//                        }

//                        override fun onItemSelected(i: Int) {
//                            val websiteUrl = remoteimages[i].websiteUrl
//                            if (i == 0) { // Check if Slide2 is clicked (i == 1)
//                                // Start the specific activity for Slide2
//                                startActivity(Intent(this@MainActivity, Ekyc::class.java))
//                            } else if (websiteUrl.isNotEmpty()) {
//                                // Open the website URL in the browser for other slides
//                                val intent = Intent(Intent.ACTION_VIEW)
//                                intent.data = Uri.parse(websiteUrl)
//                                startActivity(intent)
//                            }
//                        }

                        override fun onItemSelected(i: Int) {
                            val slide = remoteimages[i]

                            if (slide.activityName.isNotEmpty()) {
                                // Start the activity specified in the Firebase database
                                try {
                                    val activityClass = Class.forName("com.irocky.pmkisanyojna.${slide.activityName}")
                                    val intent = Intent(this@MainActivity, activityClass)
                                    startActivity(intent)
                                } catch (e: ClassNotFoundException) {
                                    // Handle the case when the specified activity class is not found
                                    Toast.makeText(this@MainActivity, "Activity not found", Toast.LENGTH_SHORT).show()
                                }
                            } else if (slide.websiteUrl.isNotEmpty()) {
                                // Open the website URL in the browser for slides with a website URL
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(slide.websiteUrl)
                                startActivity(intent)
                            }
                        }

                    })
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })

        val settingsIcon: ImageView = findViewById(R.id.setting_icon)

        settingsIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        MobileAds.initialize(this) {
        }

        mAdView = findViewById(R.id.adView)
        val adRequestBanner = AdRequest.Builder().build()
        mAdView.loadAd(adRequestBanner)

        wishM = findViewById(R.id.wish)
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greetingMessage = when (currentHour) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            else -> "Good Night"
        }
        wishM.text = greetingMessage

        val crd1 = findViewById<RelativeLayout>(R.id.awas_yojna)
        crd1.setOnClickListener {
//            loadInterstitialAd()
            show_ad()
            startActivity(Intent(this, AwasYojna::class.java))
        }

        val crd2 = findViewById<RelativeLayout>(R.id.card2)
        crd2.setOnClickListener {
//            loadInterstitialAd()
            show_ad()
            startActivity(Intent(this, KisanYojna::class.java))
        }

        val crd3 = findViewById<RelativeLayout>(R.id.cardd3)
        crd3.setOnClickListener {
//            loadInterstitialAd()
            show_ad()
            startActivity(Intent(this, Khatauni::class.java))
        }

//        val card4 = findViewById<RelativeLayout>(R.id.card4news)
//        card4.setOnClickListener {
////            loadInterstitialAd()
//            show_ad()
//            startActivity(Intent(this, NewsActivity::class.java))
//        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }

                R.id.rate_app -> {

                    val appPackageName = packageName
                    val marketUri = Uri.parse("market://details?id=$appPackageName")
                    val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")

                    try {
                        // Try to open the app's page on the Google Play Store app
                        val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
                        startActivity(marketIntent)
                    } catch (e: ActivityNotFoundException) {
                        // If the Google Play Store app is not installed, open the web page
                        val webIntent = Intent(Intent.ACTION_VIEW, webUri)
                        startActivity(webIntent)
                    }

                }

                R.id.nav_privacy -> {

                    startActivity(Intent(this, PrivacyPolicy::class.java))

//                    val privacyPolicyUrl = getString(R.string.privacy_Url)
//
//
//                    // Create an Intent to open the web browser
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
//
//                    // Check if there's a web browser app available to handle the intent
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivity(intent)
//                    } else {
//                        // Handle the case where there's no web browser app available
//
//                    }

                }

                R.id.share_app->{

                    val appPackageName = packageName
                    val marketUri = Uri.parse("market://details?id=$appPackageName")
                    val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")

                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareMessage = "Download App PM Awas Yojna : $webUri"
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

                    // Start the share activity
                    startActivity(Intent.createChooser(sharingIntent, "Share via"))

                }





            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }


    private fun loadInterstitialAd() {

        var adRequest = AdRequest.Builder().build()
        val adUnitId = getString(R.string.interstitial_id)


        InterstitialAd.load(
            this,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                            }

                            override fun onAdDismissedFullScreenContent() {
//                                    mInterstitialAd = null
                                loadInterstitialAd()

                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                mInterstitialAd = null
                            }

                            override fun onAdImpression() {
                            }

                            override fun onAdShowedFullScreenContent() {
                            }
                        }
                }
            })
    }

    fun show_ad() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
        }
    }


    fun onCardClick(view: View) {
    }


}

