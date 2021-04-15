package com.app.bitcoinapp.model

import android.content.Context
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Coin(
    @SerializedName("asset_id")
    val assetId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type_is_crypto")
    val isCrypto: Int,
    @SerializedName("data_start")
    val dataStart: String,
    @SerializedName("data_end")
    val dataEnd: String,
    @SerializedName("volume_1hrs_usd")
    var volumeHourUsd: String,
    @SerializedName("volume_1day_usd")
    var volumeDayUsd: String,
    @SerializedName("volume_1mth_usd")
    var volumeMthUsd: String,
    @SerializedName("price_usd")
    var priceUsd: String?,
    @SerializedName("id_icon")
    val iconId: String?,
    var iconUrl: String?,
    var favorite: Boolean,
): Parcelable