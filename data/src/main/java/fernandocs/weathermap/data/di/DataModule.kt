package fernandocs.weathermap.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import fernandocs.weathermap.data.ApiClientFactory
import fernandocs.weathermap.data.BuildConfig
import fernandocs.weathermap.data.bookmark.di.BookmarkModule
import fernandocs.weathermap.data.bookmark.local.BookmarkDatabase
import fernandocs.weathermap.data.weather.di.WeatherModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module(includes = [BookmarkModule::class, WeatherModule::class])
class DataModule {
    @Provides
    internal fun provideApiClientFactory(
        @Named("BASE_URL") url: String
    ): ApiClientFactory {
        val interceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val okHttp = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        return ApiClientFactory(
            Retrofit.Builder()
                .baseUrl(url)
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        )
    }

    @Provides
    fun providesBookmarkDatabase(context: Context): BookmarkDatabase {
        return Room.databaseBuilder(context, BookmarkDatabase::class.java, "bookmark-db").build()
    }
}
