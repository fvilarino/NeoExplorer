package com.francesc.neoexplorer.data.neo.impl

import androidx.collection.LruCache
import com.francesc.neoexplorer.data.neo.model.AsteroidId
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.data.neo.model.NeoFeed
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant
import kotlinx.datetime.LocalDate

private val CACHE_TTL: Duration = 10.minutes
private const val MAX_ASTEROIDS = 100
private const val MAX_FEEDS = 20

/**
 * A lightweight in-memory cache for asteroid data and feeds.
 *
 * Entries are invalidated after [CACHE_TTL].
 */
@Inject
@SingleIn(AppScope::class)
class NeoCache(private val clock: Clock) : AsteroidLocalDataSource, FeedLocalDataSource {

  private val asteroidCache = LruCache<AsteroidId, CacheEntry<NearEarthObject>>(MAX_ASTEROIDS)
  private val feedCache = LruCache<FeedKey, CacheEntry<NeoFeed>>(MAX_FEEDS)

  /** Returns the cached [NearEarthObject] if present and not expired. */
  override fun getAsteroid(id: AsteroidId): NearEarthObject? {
    return asteroidCache[id]?.takeIf { !it.isExpired(clock.now()) }?.value
  }

  /** Caches the given [NearEarthObject]. */
  override fun putAsteroid(asteroid: NearEarthObject) {
    asteroidCache.put(asteroid.id, CacheEntry(asteroid, clock.now()))
  }

  /** Returns the cached [NeoFeed] for the given date range if present and not expired. */
  override fun getFeed(startDate: LocalDate, endDate: LocalDate?): NeoFeed? {
    val key = FeedKey(startDate, endDate)
    return feedCache[key]?.takeIf { !it.isExpired(clock.now()) }?.value
  }

  /** Caches the given [NeoFeed] for the date range. */
  override fun putFeed(startDate: LocalDate, endDate: LocalDate?, feed: NeoFeed) {
    val key = FeedKey(startDate, endDate)
    feedCache.put(key, CacheEntry(feed, clock.now()))
  }

  private data class CacheEntry<T>(val value: T, val timestamp: Instant) {
    fun isExpired(now: Instant): Boolean = (now - timestamp) > CACHE_TTL
  }

  private data class FeedKey(val startDate: LocalDate, val endDate: LocalDate?)
}
