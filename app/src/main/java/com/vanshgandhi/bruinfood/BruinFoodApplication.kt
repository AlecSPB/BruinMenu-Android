package com.vanshgandhi.bruinfood

import android.app.Application
import com.squareup.moshi.Moshi
import com.vanshgandhi.bruinfood.adapters.LocalDateAdapter
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.LocalDate
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import rx.Observable
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * Created by Vansh Gandhi on 12/22/16.
 * Copyright © 2016
 */
class BruinFoodApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val moshi = Moshi.Builder().add(LocalDateAdapter()).build()
        val moshiConverterFactor = MoshiConverterFactory.create()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val retrofit = Retrofit.Builder()
                .baseUrl("https://salty-shelf-63361.herokuapp.com")
//                .client(OkHttpClient.Builder().addInterceptor(logger).build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(moshiConverterFactor)
                .build()
        menuApi = retrofit.create(MenuApi::class.java)

        val mockRetrofit = MockRetrofit.Builder(retrofit).build()
        val delegate = mockRetrofit.create(MenuApi::class.java)
        menuApi = object : MenuApi {
            override fun getHours(date: LocalDate?): Observable<List<Models.Hours>> {
                val str = stringFromAssets("sample_hours_response.json")
                val adapter = moshi.adapter(Models.Hours::class.java)
                val response = Calls.response(adapter.fromJson(str))
                return delegate.returning(response).getHours(date)
            }

            override fun getMenuOverview(date: LocalDate?): Observable<Models.MenuOverview> {
                val str = stringFromAssets("sample_overview_response.json")
                val adapter = moshi.adapter(Models.MenuOverview::class.java)
                val response = Calls.response(adapter.fromJson(str))
                return delegate.returning(response).getMenuOverview(date)
            }

            override fun getFullMenu(hallCode: Int, date: LocalDate?): Observable<Models.Menu> {
                val str = stringFromAssets("sample_full_response.json")
                val adapter = moshi.adapter(Models.Menu::class.java)
                val response = Calls.response(adapter.fromJson(str))
                return delegate.returning(response).getFullMenu(hallCode, date)
            }

            override fun getFoodDetails(foodId: String): Observable<Models.Food> {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
//        daggerNetwork = DaggerNetwork.create()
    }

    private fun stringFromAssets(fileName: String): String {
        val buf = StringBuilder()
        val json = assets.open(fileName)
        val br = BufferedReader(InputStreamReader(json))
        var str = br.readLine()

        while (str != null) {
            buf.append(str)
            str = br.readLine()
        }

        br.close()
        return buf.toString()
    }

    companion object {
        lateinit var menuApi: MenuApi
    }
}
