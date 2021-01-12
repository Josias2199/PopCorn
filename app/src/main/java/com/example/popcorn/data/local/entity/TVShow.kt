package com.example.popcorn.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable



@Entity(tableName = "tvShows")
data class TVShow (

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,

    @SerializedName( "name")
    val name: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName( "country")
    val country: String,

    @SerializedName( "network")
    val network: String,

    @SerializedName("status")
    val status: String,

    @SerializedName( "image_thumbnail_path")
    val imageThumbnail: String
) : Serializable