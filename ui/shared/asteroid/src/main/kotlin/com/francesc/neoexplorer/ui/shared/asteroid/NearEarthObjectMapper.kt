package com.francesc.neoexplorer.ui.shared.asteroid

import com.francesc.neoexplorer.core.formatter.DateFormatter
import com.francesc.neoexplorer.data.neo.model.CloseApproachData
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.data.neo.model.NeoFeed
import dev.zacsweers.metro.Inject
import kotlinx.datetime.LocalDate

/**
 * Single source of truth for mapping [NearEarthObject] domain models to [AsteroidUiModel] UI
 * models, shared by all presenters that display asteroid lists.
 *
 * Two mapping modes are provided:
 * - [toFeedUiModel] — for feed-based screens (Dashboard, Temporal Explorer). Selects the
 *   close-approach entry that matches the feed date so the displayed data stays coherent with the
 *   day the asteroid was included in the feed.
 * - [toBrowseUiModel] — for the paged Browse screen, where there is no single feed date; the first
 *   available close-approach entry is used instead.
 *
 * Use [parseFeed] to convert a [NeoFeed] into a [ParsedFeed] (sorted list + hazardous count).
 */
@Inject
class NearEarthObjectMapper(private val dateFormatter: DateFormatter) {

  /**
   * Maps a [NearEarthObject] to an [AsteroidUiModel] using [feedDate] to locate the most relevant
   * close-approach data entry. Falls back to the first entry when none matches [feedDate].
   */
  fun toFeedUiModel(neo: NearEarthObject, feedDate: LocalDate): AsteroidUiModel {
    val approach =
      neo.closeApproachData.find { it.closeApproachDate == feedDate }
        ?: neo.closeApproachData.firstOrNull()
    return neo.toUiModel(approach)
  }

  /**
   * Maps a [NearEarthObject] to an [AsteroidUiModel] for the Browse screen, where no single feed
   * date is relevant. Uses the first available close-approach entry.
   */
  fun toBrowseUiModel(neo: NearEarthObject): AsteroidUiModel =
    neo.toUiModel(neo.closeApproachData.firstOrNull())

  /**
   * Converts a [NeoFeed] into a [ParsedFeed]: all near-earth objects sorted by date, each mapped
   * with [toFeedUiModel], plus a pre-computed count of potentially-hazardous asteroids.
   */
  fun parseFeed(feed: NeoFeed): ParsedFeed {
    val asteroids =
      feed.nearEarthObjects.entries
        .sortedBy { it.key }
        .flatMap { (date, neos) -> neos.map { neo -> toFeedUiModel(neo, date) } }
    return ParsedFeed(asteroids, hazardousCount = asteroids.count { it.isPotentiallyHazardous })
  }

  private fun NearEarthObject.toUiModel(approach: CloseApproachData?): AsteroidUiModel {
    val dist = approach?.missDistanceKm?.value?.let { Distance.km(it) } ?: Distance.UNKNOWN
    return AsteroidUiModel(
      id = AsteroidId(id.value),
      name = name,
      absoluteMagnitudeH = absoluteMagnitudeH,
      missDistance = dist,
      isPotentiallyHazardous = isPotentiallyHazardousAsteroid,
      velocity = approach?.relativeVelocityKmPerSecond?.value?.let(::Velocity) ?: Velocity.UNKNOWN,
      estimatedDiameterMaxKm = estimatedDiameter.maxKm.value,
      closeApproachDate = approach?.closeApproachDate?.let { dateFormatter.format(it) }.orEmpty(),
      threatLevel = ThreatLevel.from(dist),
    )
  }
}
