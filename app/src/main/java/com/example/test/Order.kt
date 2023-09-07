package com.example.test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val meat: MeatOptions,
    val bun: BunOptions,
    val lettuce: Boolean,
    val tomato: Boolean,
    val onion: Boolean
) :Parcelable
