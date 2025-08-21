package com.plcoding.bookpedia.core.data

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException

suspend inline fun <reified T> safeHttpCall(
  execute: () -> HttpResponse
): Result<T, DataError.RemoteError> {
  val response = try {
    execute()
  } catch (e: SocketTimeoutException) {
    return Result.Error(DataError.RemoteError.REQUEST_TIMEOUT)
  } catch (e: UnresolvedAddressException) {
    return Result.Error(DataError.RemoteError.NO_INTERNET_CONNECTION)
  } catch (e: Exception) {
    return Result.Error(DataError.RemoteError.UNKNOWN)
  }
  return responseToResult<T>(response)
}

suspend inline fun <reified T> responseToResult(
  httpResponse: HttpResponse
): Result<T, DataError.RemoteError> {
  return when (httpResponse.status.value) {
    in 200..299 -> {
      try {
        Result.Success(httpResponse.body<T>())
      } catch (e: Exception) {
        Result.Error(DataError.RemoteError.SERIALIZATION_ERROR)
      }
    }

    404 -> Result.Error(DataError.RemoteError.REQUEST_TIMEOUT)
    429 -> Result.Error(DataError.RemoteError.TOO_MANY_REQUESTS)
    in 500..599 -> {
      Result.Error(DataError.RemoteError.SERVER_ERROR)
    }

    else -> Result.Error(DataError.RemoteError.UNKNOWN)
  }
}