package com.example.moviedbapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieGenreResponse(

    @field:SerializedName("genres")
    val genres: List<GenresItem>
)

@Parcelize
data class GenresItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable