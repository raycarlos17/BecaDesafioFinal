package com.app.bitcoinapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
    val volumeHourUsd: String,
    @SerializedName("volume_1day_usd")
    val volumeDayUsd: String,
    @SerializedName("volume_1mth_usd")
    val volumeMthUsd: String,
    @SerializedName("price_usd")
    val priceUsd: String
): Parcelable
