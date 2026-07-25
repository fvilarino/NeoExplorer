package com.francesc.neoexplorer.ui.shared.asteroid

/**
 * Represents a miss-distance value, stored internally as kilometres.
 *
 * Modelled after Kotlin's [kotlin.time.Duration]: the raw value is kept private and callers
 * retrieve the measurement in the unit they need via [inKilometers] or [inLunarDistances].
 *
 * Create instances with the factory functions [Distance.km] or [Distance.lunar].
 */
class Distance private constructor(private val rawKm: Double) {

  /** `true` if this distance holds a real value; `false` for [UNKNOWN]. */
  val isKnown: Boolean
    get() = !rawKm.isNaN()

  /** The distance expressed in kilometres, or [Double.NaN] if unknown. */
  val inKilometers: Double
    get() = rawKm

  /** The distance expressed in lunar distances, or [Double.NaN] if unknown. */
  val inLunarDistances: Double
    get() = rawKm / LUNAR_DISTANCE_KM

  /**
   * Returns [inLunarDistances], or `null` if this distance is [UNKNOWN]. Useful for null-safe
   * comparators (e.g. sorting).
   */
  val inLunarDistancesOrNull: Double?
    get() = if (isKnown) inLunarDistances else null

  // ── equals / hashCode / toString ─────────────────────────────────────────────

  /**
   * Two [Distance] values are equal when their raw km bits are identical. This means two [UNKNOWN]
   * instances are considered equal, unlike the default IEEE 754 behaviour where `NaN != NaN`.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Distance) return false
    return rawKm.toBits() == other.rawKm.toBits()
  }

  override fun hashCode(): Int = rawKm.toBits().hashCode()

  override fun toString(): String = if (isKnown) "Distance($rawKm km)" else "Distance.UNKNOWN"

  // ── Companion ─────────────────────────────────────────────────────────────────

  companion object {
    /** Number of kilometres in one lunar distance. */
    const val LUNAR_DISTANCE_KM: Double = 384_400.0

    /** Sentinel value representing an unknown or unavailable distance. */
    val UNKNOWN = Distance(Double.NaN)

    /** Creates a [Distance] from a value already expressed in kilometres. */
    fun km(value: Double): Distance = Distance(value)

    /** Creates a [Distance] from a value expressed in lunar distances. */
    fun lunar(value: Double): Distance = Distance(value * LUNAR_DISTANCE_KM)
  }
}
