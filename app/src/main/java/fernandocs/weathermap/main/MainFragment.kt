package fernandocs.weathermap.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.AndroidSupportInjection
import fernandocs.weathermap.BookmarkAdapter
import fernandocs.weathermap.BookmarkOnClickListener
import fernandocs.weathermap.R
import fernandocs.weathermap.di.ViewModelFactory
import fernandocs.weathermap.hideMeAnimated
import fernandocs.weathermap.showMeAnimated
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraIdleListener, BookmarkOnClickListener {

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<MainViewModel>

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer(this::render))
        viewModel.navigate.observe(viewLifecycleOwner, Observer(this::navigate))
        viewModel.start()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        BottomSheetBehavior.from(view_bookmarks)
            .setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        mMap?.uiSettings?.setAllGesturesEnabled(false)
                        locationInfoView.hideMeAnimated()
                        pin.hideMeAnimated()
                        helpButton.hideMeAnimated()
                    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        mMap?.uiSettings?.setAllGesturesEnabled(true)
                        locationInfoView.showMeAnimated()
                        pin.showMeAnimated()
                        helpButton.showMeAnimated()
                        mMap?.cameraPosition?.target?.let {
                            viewModel.handleIntent(
                                ComparisonIntent.LoadWeather(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })

        BottomSheetBehavior.from(view_bookmarks)?.apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        bookmarksList.adapter = BookmarkAdapter(this@MainFragment)

        buttonAddBookmark.setOnClickListener {
            viewModel.handleIntent(ComparisonIntent.SaveBookmark)

            AnimatorSet().apply {
                playSequentially(ObjectAnimator.ofFloat(
                    view_bookmarks,
                    View.TRANSLATION_Y,
                    0f,
                    -150f
                ).apply {
                    duration = 500
                    interpolator = AccelerateDecelerateInterpolator()
                }, ObjectAnimator.ofFloat(
                    view_bookmarks,
                    View.TRANSLATION_Y,
                    -150f,
                    0f
                ).apply {
                    duration = 500
                    interpolator = AccelerateDecelerateInterpolator()
                })
                start()
            }
        }
        helpButton.setOnClickListener {
            viewModel.handleIntent(ComparisonIntent.HelpButtonTapped)
        }
    }

    private fun render(viewState: MainViewState) {
        (bookmarksList.adapter as? BookmarkAdapter)?.update(viewState.bookmarks.reversed())

        viewState.weatherViewState?.let {
            textWeatherCityTitle.text = "Weather in ${it.cityName}"
            textWeatherTemperature.text = it.temperature + "ºC"
            textWeatherHumidity.text = "Humidity ${it.humidity} %"
            textWeatherWind.text = "Wind ${it.wind} m/s"
            textWeatherRain.visibility = if (it.rain == null) View.INVISIBLE else View.VISIBLE

            it.rain?.let {
                textWeatherRain.text = "Rain $it"
            }

            Glide.with(this)
                .load(it.temperatureIconUrl)
                .centerCrop()
                .into(imageWeatherIcon)

            buttonAddBookmark.setImageResource(
                if (it.isBookmark) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            )

            mMap?.let { map ->
                val location = LatLng(it.lat, it.lon)
                map.moveCamera(CameraUpdateFactory.newLatLng(location))
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 8f))
            }
        }
    }

    private fun navigate(command: MainNavigationCommand) {
        when (command) {
            is MainNavigationCommand.ToHelp -> {
                findNavController().navigate(R.id.action_mainFragment_to_helpFragment)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = it

            // maps events we need to respond to
            it.setOnCameraMoveStartedListener(this@MainFragment)
            it.setOnCameraIdleListener(this@MainFragment)
        }
    }

    override fun onCameraMoveStarted(reason: Int) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            locationInfoView.alpha = 0f
            helpButton.alpha = 0f
            BottomSheetBehavior.from(view_bookmarks)?.apply {
                state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    override fun onCameraIdle() {
        BottomSheetBehavior.from(view_bookmarks)?.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onItemClick(id: Int) {
        viewModel.handleIntent(ComparisonIntent.LoadBookmarkWeather(id))
        BottomSheetBehavior.from(view_bookmarks)?.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onDeleteClick(id: Int) {
        viewModel.handleIntent(ComparisonIntent.DeleteBookmark(id))
    }
}