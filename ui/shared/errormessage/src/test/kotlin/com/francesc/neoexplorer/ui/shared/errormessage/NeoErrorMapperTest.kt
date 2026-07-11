package com.francesc.neoexplorer.ui.shared.errormessage

import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class NeoErrorMapperTest {

  private val resources: Resources = ApplicationProvider.getApplicationContext<Context>().resources

  // ── Network unavailable ───────────────────────────────────────────────────

  @Test
  fun `UnknownHostException returns network unavailable message`() {
    assertEquals(
      resources.getString(R.string.error_network_unavailable),
      UnknownHostException("Unable to resolve host").toUserMessage(resources),
    )
  }

  @Test
  fun `ConnectException returns network unavailable message`() {
    assertEquals(
      resources.getString(R.string.error_network_unavailable),
      ConnectException("Connection refused").toUserMessage(resources),
    )
  }

  @Test
  fun `SocketTimeoutException returns network unavailable message`() {
    assertEquals(
      resources.getString(R.string.error_network_unavailable),
      SocketTimeoutException("timeout").toUserMessage(resources),
    )
  }

  @Test
  fun `IOException wrapping UnknownHostException returns network unavailable message`() {
    assertEquals(
      resources.getString(R.string.error_network_unavailable),
      IOException("wrapper", UnknownHostException("no host")).toUserMessage(resources),
    )
  }

  // ── HTTP 429 rate limit ───────────────────────────────────────────────────

  @Test
  fun `HTTP 429 exception returns rate limited message`() {
    assertEquals(
      resources.getString(R.string.error_rate_limited),
      RuntimeException("HTTP 429 Too Many Requests").toUserMessage(resources),
    )
  }

  @Test
  fun `exception whose cause has HTTP 429 message returns rate limited message`() {
    val cause = RuntimeException("HTTP 429 Too Many Requests")
    assertEquals(
      resources.getString(R.string.error_rate_limited),
      RuntimeException("wrapper", cause).toUserMessage(resources),
    )
  }

  // ── HTTP 404 not found ────────────────────────────────────────────────────

  @Test
  fun `HTTP 404 exception returns not found message`() {
    assertEquals(
      resources.getString(R.string.error_not_found),
      RuntimeException("HTTP 404 Not Found").toUserMessage(resources),
    )
  }

  // ── HTTP 5xx server error ─────────────────────────────────────────────────

  @Test
  fun `HTTP 500 exception returns server error message`() {
    assertEquals(
      resources.getString(R.string.error_server),
      RuntimeException("HTTP 500 Internal Server Error").toUserMessage(resources),
    )
  }

  @Test
  fun `HTTP 503 exception returns server error message`() {
    assertEquals(
      resources.getString(R.string.error_server),
      RuntimeException("HTTP 503 Service Unavailable").toUserMessage(resources),
    )
  }

  // ── Generic fallback ──────────────────────────────────────────────────────

  @Test
  fun `unknown exception returns generic fallback message`() {
    assertEquals(
      resources.getString(R.string.error_unexpected),
      RuntimeException("Something completely unexpected").toUserMessage(resources),
    )
  }

  @Test
  fun `exception with null message returns generic fallback message`() {
    assertEquals(
      resources.getString(R.string.error_unexpected),
      RuntimeException(null as String?).toUserMessage(resources),
    )
  }

  @Test
  fun `HTTP 200 does not match any special case and returns generic fallback`() {
    // Sanity check: only error codes trigger special messages
    assertEquals(
      resources.getString(R.string.error_unexpected),
      RuntimeException("HTTP 200 OK").toUserMessage(resources),
    )
  }
}
