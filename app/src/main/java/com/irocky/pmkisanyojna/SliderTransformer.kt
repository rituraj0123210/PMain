package com.irocky.pmkisanyojna

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class SliderTransformer : ViewPager2.PageTransformer {

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }

    override fun transformPage(page: View, position: Float) {
        val scaleFactor = MIN_SCALE.coerceAtLeast(1 - abs(position))
        var alpha = (MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA))

        page.apply {
            scaleX = scaleFactor
            scaleY = scaleFactor
            alpha = alpha
        }
    }
}
