package com.francesc.neoexplorer.ui.shared.errormessage

import android.content.res.Resources
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Maps a [Throwable] from the data/network layer into a concise, user-friendly error message.
 *
 * The returned string is loaded from [resources] so it respects the device locale.
 *
 * Categories handled:
 * - Offline / unreachable host → [R.string.error_network_unavailable]
 * - HTTP 429 Too Many Requests → [R.string.error_rate_limited]
 * - HTTP 404 Not Found → [R.string.error_not_found]
 * - HTTP 5xx Server Error → [R.string.error_server]
 * - Everything else → [R.string.error_unexpected]
 */
fun Throwable.toUserMessage(resources: Resources): String =
  when {
    isNetworkUnavailable() -> resources.getString(R.string.error_network_unavailable)
    isRateLimited() -> resources.getString(R.string.error_rate_limited)
    isNotFound() -> resources.getString(R.string.error_not_found)
    isServerError() -> resources.getString(R.string.error_server)
    else -> resources.getString(R.string.error_unexpected)
  }

private fun Throwable.isNetworkUnavailable(): Boolean =
  this is UnknownHostException ||
    this is ConnectException ||
    this is SocketTimeoutException ||
    (this is IOException && cause?.isNetworkUnavailable() == true)

/**
 * Retrofit's HttpException message is formatted as "HTTP <code> <reason>". We match by inspecting
 * the message string to avoid a direct Retrofit dependency in this module.
 */
private fun Throwable.httpCode(): Int? =
  message?.let { msg ->
    val httpPrefix = "HTTP "
    if (msg.startsWith(httpPrefix)) {
      msg.removePrefix(httpPrefix).substringBefore(' ').toIntOrNull()
    } else {
      null
    }
  } ?: cause?.httpCode()

private fun Throwable.isRateLimited(): Boolean = httpCode() == 429

private fun Throwable.isNotFound(): Boolean = httpCode() == 404

private fun Throwable.isServerError(): Boolean = httpCode()?.let { it in 500..599 } == true
