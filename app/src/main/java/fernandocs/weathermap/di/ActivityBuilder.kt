package fernandocs.weathermap.di

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import fernandocs.weathermap.MainActivity

@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity
}
