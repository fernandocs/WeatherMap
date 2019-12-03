package fernandocs.weathermap.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import fernandocs.weathermap.MainActivity
import fernandocs.weathermap.main.MainFragment

@Module
abstract class MainActivityModule {
    @PerFragment
    @ContributesAndroidInjector
    abstract fun mainFragmentInjector(): MainFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideActivity(mainActivity: MainActivity) = mainActivity
    }
}
