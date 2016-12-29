package com.vanshgandhi.bruinfood

import org.joda.time.LocalDate
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Vansh Gandhi on 12/18/16.
 * Copyright © 2016
 */


interface MenuApi {
    @GET("hours")
    fun getHours(@Query("date") date: LocalDate? = null): Observable<List<Models.Hours>>

    @GET("menu")
    fun getMenuOverview(@Query("date") date: LocalDate? = null): Observable<Models.MenuOverview> //TODO

    @GET("menu/{hallCode}")
    fun getFullMenu(@Path("hallCode") hallCode: Int, @Query("date") date: LocalDate? = null): Observable<Models.Menu> //TODO

    @GET("menu/item/{id}")
    fun getFoodDetails(@Path("id") foodId: String): Observable<Models.Food>

    //TODO: Geofence monitoring
}