package com.plcoding.bookpedia.core.domain

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.error_disk_full
import cmp_bookpedia.composeapp.generated.resources.error_no_internet_connection
import cmp_bookpedia.composeapp.generated.resources.error_request_timeout
import cmp_bookpedia.composeapp.generated.resources.error_serialization_error
import cmp_bookpedia.composeapp.generated.resources.error_server_error
import cmp_bookpedia.composeapp.generated.resources.error_too_many_requests
import cmp_bookpedia.composeapp.generated.resources.error_unknown
import com.plcoding.bookpedia.core.presentation.UiText

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.LocalError.DISK_FULL -> Res.string.error_disk_full
        DataError.LocalError.UNKNOWN -> Res.string.error_unknown
        DataError.RemoteError.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.RemoteError.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.RemoteError.NO_INTERNET_CONNECTION -> Res.string.error_no_internet_connection
        DataError.RemoteError.SERVER_ERROR -> Res.string.error_server_error
        DataError.RemoteError.SERIALIZATION_ERROR -> Res.string.error_serialization_error
        DataError.RemoteError.UNKNOWN -> Res.string.error_unknown
    }
    return UiText.StringResourceId(stringRes)
}