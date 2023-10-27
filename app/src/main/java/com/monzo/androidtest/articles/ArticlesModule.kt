package com.monzo.androidtest.articles

import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import com.monzo.androidtest.R
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.database.FavoritesDao
import com.monzo.androidtest.api.database.GuardianDatabase
import com.monzo.androidtest.articles.model.ArticleMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class ArticlesModule {
    @RequiresApi(Build.VERSION_CODES.N)
    fun inject(context: Context): ArticlesViewModel {
        return ArticlesViewModel(ArticlesRepository(createGuardianService(context), ArticleMapper()), favoritesDao(context))
    }
    private fun favoritesDao(context: Context): FavoritesDao =
        GuardianDatabase.getInstance(context).favoritesDAO()
    private fun createGuardianService(context: Context): GuardianService {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(createOkHttpClient(context.resources))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GuardianService::class.java)
    }

    private fun createOkHttpClient(resources: Resources): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val clientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(getAuthInterceptor(resources))
            addInterceptor(loggingInterceptor)
        }
        return clientBuilder.build()
    }

    private fun getAuthInterceptor(resources: Resources): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val hb = original.headers.newBuilder()
            hb.add(HEADER_API_KEY, resources.getString(R.string.guardian_api_key))
            chain.proceed(original.newBuilder().headers(hb.build()).build())
        }
    }

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
        private const val HEADER_API_KEY = "api-key"
    }
}
