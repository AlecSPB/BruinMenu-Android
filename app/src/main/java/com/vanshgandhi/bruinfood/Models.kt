package com.vanshgandhi.bruinfood

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by Vansh Gandhi on 12/18/16.
 * Copyright © 2016
 */

object Models {
    data class Hours(@Json(name = "hall_name") val hallName: String,
                     val breakfast: String, val lunch: String,
                     val dinner: String,
                     val lateNight: String) : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<Hours> = object : Parcelable.Creator<Hours> {
                override fun createFromParcel(source: Parcel): Hours = Hours(source)
                override fun newArray(size: Int): Array<Hours?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readString())

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(hallName)
            dest?.writeString(breakfast)
            dest?.writeString(lunch)
            dest?.writeString(dinner)
            dest?.writeString(lateNight)
        }
    }

    data class Food(val id: String,
                    val name: String,
                    val ingredients: String,
                    val nutrition: Nutrition,
                    @Json(name = "special_properties") val specialProperties: SpecialProperties) : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<Food> = object : Parcelable.Creator<Food> {
                override fun createFromParcel(source: Parcel): Food = Food(source)
                override fun newArray(size: Int): Array<Food?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readParcelable<Nutrition>(Nutrition::class.java.classLoader), source.readParcelable<SpecialProperties>(SpecialProperties::class.java.classLoader))

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(id)
            dest?.writeString(name)
            dest?.writeString(ingredients)
            dest?.writeParcelable(nutrition, 0)
            dest?.writeParcelable(specialProperties, 0)
        }
    }

    data class SpecialProperties(val vegetarian: Boolean = false,
                                 val vegan: Boolean = false,
                                 @Json(name = "low_carbon") val lowCarbon: Boolean = false,
                                 val milk: Boolean = false,
                                 val eggs: Boolean = false,
                                 val fish: Boolean = false,
                                 val shellfish: Boolean = false,
                                 @Json(name = "tree_nuts") val treeNuts: Boolean = false,
                                 val peanuts: Boolean = false,
                                 val wheat: Boolean = false,
                                 val soybeans: Boolean = false) : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<SpecialProperties> = object : Parcelable.Creator<SpecialProperties> {
                override fun createFromParcel(source: Parcel): SpecialProperties = SpecialProperties(source)
                override fun newArray(size: Int): Array<SpecialProperties?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt(), 1 == source.readInt())

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeInt((if (vegetarian) 1 else 0))
            dest?.writeInt((if (vegan) 1 else 0))
            dest?.writeInt((if (lowCarbon) 1 else 0))
            dest?.writeInt((if (milk) 1 else 0))
            dest?.writeInt((if (eggs) 1 else 0))
            dest?.writeInt((if (fish) 1 else 0))
            dest?.writeInt((if (shellfish) 1 else 0))
            dest?.writeInt((if (treeNuts) 1 else 0))
            dest?.writeInt((if (peanuts) 1 else 0))
            dest?.writeInt((if (wheat) 1 else 0))
            dest?.writeInt((if (soybeans) 1 else 0))
        }
    }

    data class Nutrition(@Json(name = "serving_size") val servingSize: String,
                         @Json(name = "amt_per_serving") val amountPerServing: String,
                         @Json(name = "calories") val calories: String,
                         @Json(name = "fat_cal") val fatCalories: String,
                         @Json(name = "total_fat_percent") val totalFatPercent: String,
                         @Json(name = "total_fat_grams") val totalFatGrams: String,
                         @Json(name = "sat_fat_percent") val saturatedFatPercent: String,
                         @Json(name = "sat_fat_grams") val saturatedFatGrams: String,
                         @Json(name = "trans_fat_percent") val transFatPercent: String,
                         @Json(name = "cholesterol_percent") val cholesterolPercent: String,
                         @Json(name = "cholesterol_grams") val cholesterolGrams: String,
                         @Json(name = "sodium_percent") val sodiumPercent: String,
                         @Json(name = "sodium_grams") val sodiumGrams: String,
                         @Json(name = "total_carb_percent") val totalCarbohydratesPercent: String,
                         @Json(name = "total_carb_grams") val totalCarbohydratesGrams: String,
                         @Json(name = "fiber_percent") val fiberPercent: String,
                         @Json(name = "fiber_grams") val fiberGrams: String,
                         @Json(name = "sugar_grams") val sugarsGrams: String,
                         @Json(name = "protein_grams") val proteinGrams: String,
                         @Json(name = "vitamin_a_percent") val vitaminAPercent: String = "0%",
                         @Json(name = "vitamin_c_percent") val vitaminCPercent: String = "0%",
                         @Json(name = "calcium_percent") val calciumPercent: String = "0%",
                         @Json(name = "iron_percent") val ironPercent: String = "0%") : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<Nutrition> = object : Parcelable.Creator<Nutrition> {
                override fun createFromParcel(source: Parcel): Nutrition = Nutrition(source)
                override fun newArray(size: Int): Array<Nutrition?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString())

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(servingSize)
            dest?.writeString(amountPerServing)
            dest?.writeString(calories)
            dest?.writeString(fatCalories)
            dest?.writeString(totalFatPercent)
            dest?.writeString(totalFatGrams)
            dest?.writeString(saturatedFatPercent)
            dest?.writeString(saturatedFatGrams)
            dest?.writeString(transFatPercent)
            dest?.writeString(cholesterolPercent)
            dest?.writeString(cholesterolGrams)
            dest?.writeString(sodiumPercent)
            dest?.writeString(sodiumGrams)
            dest?.writeString(totalCarbohydratesPercent)
            dest?.writeString(totalCarbohydratesGrams)
            dest?.writeString(fiberPercent)
            dest?.writeString(fiberGrams)
            dest?.writeString(sugarsGrams)
            dest?.writeString(proteinGrams)
            dest?.writeString(vitaminAPercent)
            dest?.writeString(vitaminCPercent)
            dest?.writeString(calciumPercent)
            dest?.writeString(ironPercent)
        }
    }

    data class Section(val name: String, @Json(name = "food_items") val foodItems: List<Food>) : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<Section> = object : Parcelable.Creator<Section> {
                override fun createFromParcel(source: Parcel): Section = Section(source)
                override fun newArray(size: Int): Array<Section?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.readString(), source.createTypedArrayList(Food.CREATOR))

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(name)
            dest?.writeTypedList(foodItems)
        }
    }

    data class Menu(val name: String, val sections: List<Section>) : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<Menu> = object : Parcelable.Creator<Menu> {
                override fun createFromParcel(source: Parcel): Menu = Menu(source)
                override fun newArray(size: Int): Array<Menu?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.readString(), source.createTypedArrayList(Section.CREATOR))

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(name)
            dest?.writeTypedList(sections)
        }
    }

    data class MenuOverview(val menus: List<Menu>) : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<MenuOverview> = object : Parcelable.Creator<MenuOverview> {
                override fun createFromParcel(source: Parcel): MenuOverview = MenuOverview(source)
                override fun newArray(size: Int): Array<MenuOverview?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.createTypedArrayList(Menu.CREATOR))

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeTypedList(menus)
        }
    }
}