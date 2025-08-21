package com.plcoding.bookpedia.book.domain

import kotlinx.serialization.Serializable

@Serializable
data class Book(
  val id: String,
  val title: String,
  val imageUrl: String?=null,
  val authors: List<String>?=null,
  val description:  String?=null,
  val languages: List<String>?=null,
  val firstPublishYear: String?=null,
  val averageRating: Double?=null,
  val ratingsCount: Int?=null,
  val numPages: Int?=null,
  val numEditions: Int?=null

)
