package com.francesc.neoexplorer.data.neo.impl

import com.francesc.neoexplorer.data.neo.impl.mapper.toDomain
import com.francesc.neoexplorer.data.neo.model.AsteroidId
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NeoCacheTest {
  private val testClock = TestClock()
  private val cache = NeoCache(testClock)

  @Test
  fun `getAsteroid returns null when empty`() {
    assertNull(cache.getAsteroid(AsteroidId("1")))
  }

  @Test
  fun `getAsteroid returns cached value`() {
    val asteroid = nearEarthObjectDto(id = "1").toDomain()
    cache.putAsteroid(asteroid)
    assertEquals(asteroid, cache.getAsteroid(AsteroidId("1")))
  }

  @Test
  fun `getAsteroid returns null when expired`() {
    val asteroid = nearEarthObjectDto(id = "1").toDomain()
    cache.putAsteroid(asteroid)
    testClock.advance(11.minutes)
    assertNull(cache.getAsteroid(AsteroidId("1")))
  }

  @Test
  fun `getFeed returns cached value`() {
    val feed = neoFeedResponse().toDomain()
    val start = LocalDate(2025, 1, 15)
    cache.putFeed(start, null, feed)
    assertEquals(feed, cache.getFeed(start, null))
  }

  @Test
  fun `getFeed returns null when expired`() {
    val feed = neoFeedResponse().toDomain()
    val start = LocalDate(2025, 1, 15)
    cache.putFeed(start, null, feed)
    testClock.advance(11.minutes)
    assertNull(cache.getFeed(start, null))
  }

  @Test
  fun `getFeed distinguishes between different date ranges`() {
    val feed1 = neoFeedResponse(elementCount = 1).toDomain()
    val feed2 = neoFeedResponse(elementCount = 2).toDomain()
    val start1 = LocalDate(2025, 1, 15)
    val start2 = LocalDate(2025, 1, 16)

    cache.putFeed(start1, null, feed1)
    cache.putFeed(start2, null, feed2)

    assertEquals(feed1, cache.getFeed(start1, null))
    assertEquals(feed2, cache.getFeed(start2, null))
  }

  @Test
  fun `asteroid cache evicts least recently used items`() {
    // Fill the cache to capacity (100)
    for (i in 1..100) {
      cache.putAsteroid(nearEarthObjectDto(id = "$i").toDomain())
    }

    // Add one more to trigger eviction of asteroid "1"
    cache.putAsteroid(nearEarthObjectDto(id = "101").toDomain())

    assertNull(cache.getAsteroid(AsteroidId("1")))
    assertEquals(AsteroidId("2"), cache.getAsteroid(AsteroidId("2"))?.id)
    assertEquals(AsteroidId("101"), cache.getAsteroid(AsteroidId("101"))?.id)
  }

  @Test
  fun `feed cache evicts least recently used items`() {
    // Fill the cache to capacity (20)
    for (i in 1..20) {
      val start = LocalDate(2025, 1, i)
      cache.putFeed(start, null, neoFeedResponse().toDomain())
    }

    // Add one more to trigger eviction of feed "1"
    val start21 = LocalDate(2025, 1, 21)
    cache.putFeed(start21, null, neoFeedResponse().toDomain())

    assertNull(cache.getFeed(LocalDate(2025, 1, 1), null))
    assertEquals(
      AsteroidId("54321"),
      cache.getFeed(start21, null)?.nearEarthObjects?.values?.first()?.first()?.id,
    )
  }

  private class TestClock : Clock {
    private var currentInstant = Instant.fromEpochMilliseconds(0)

    override fun now(): Instant = currentInstant

    fun advance(duration: Duration) {
      currentInstant += duration
    }
  }
}
