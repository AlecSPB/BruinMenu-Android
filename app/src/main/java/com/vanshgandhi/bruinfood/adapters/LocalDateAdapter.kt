package com.vanshgandhi.bruinfood.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.LocalDate

/**
 * Created by Vansh Gandhi on 12/24/16.
 * Copyright © 2016
 */
class LocalDateAdapter {
    @ToJson
    fun toJson(localDate: LocalDate): String {
        return localDate.toString("MM/dd/yyyy")
    }

    @FromJson
    fun fromJson(localDate: String): LocalDate {
        return LocalDate.parse(localDate)
    }
}