package fernandocs.weathermap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = supportFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
