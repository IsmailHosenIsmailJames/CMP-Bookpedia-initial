package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedBookDto(
  @SerialName("key") val id: String,
  @SerialName("language") val languages : List<String>,
  @SerialName("author_key") val authorKeys: List<String>,
  @SerialName("author_name") val authors: List<String>,
  @SerialName("title") val title: String,
  @SerialName("cover_i") val coverAlternativeKey : String,
  @SerialName("first_publish_year") val firstPublishYear: Int,
  @SerialName("ratings_count") val ratingsCount: Int,
  @SerialName("ratings_average") val ratingsAverage: Double,
  @SerialName("cover_edition_key") val coverEditionKey: String,
  @SerialName("number_of_pages_median") val numberOfPagesMedian: Int,
  @SerialName("edition_count") val editionCount: Int,
  )
