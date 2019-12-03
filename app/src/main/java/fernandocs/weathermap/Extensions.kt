package fernandocs.weathermap

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

fun View.showMeAnimated() {
    ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1.3f, 1f),
        PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 1.3f, 1f),
        PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
    ).apply {
        duration = 1000
        interpolator = OvershootInterpolator()
        start()
    }
}

fun View.hideMeAnimated() {
    ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.2f, 0f),
        PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.2f, 0f),
        PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)
    ).apply {
        duration = 500
        interpolator = DecelerateInterpolator()
        start()
    }
}