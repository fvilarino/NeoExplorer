package com.francesc.neoexplorer.data.neo.impl

import com.francesc.neoexplorer.data.neo.model.AsteroidId
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.data.neo.model.NeoFeed
import kotlinx.datetime.LocalDate

interface AsteroidLocalDataSource {
  /** Returns the cached [NearEarthObject] if present and not expired. */
  fun getAsteroid(id: AsteroidId): NearEarthObject?

  /** Caches the given [NearEarthObject]. */
  fun putAsteroid(asteroid: NearEarthObject)
}

interface FeedLocalDataSource {
  /** Returns the cached [NeoFeed] for the given date range if present and not expired. */
  fun getFeed(startDate: LocalDate, endDate: LocalDate?): NeoFeed?

  /** Caches the given [NeoFeed] for the date range. */
  fun putFeed(startDate: LocalDate, endDate: LocalDate?, feed: NeoFeed)
}
