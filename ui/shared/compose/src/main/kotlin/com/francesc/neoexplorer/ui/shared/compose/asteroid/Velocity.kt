package com.francesc.neoexplorer.ui.shared.compose.asteroid

@JvmInline
value class Velocity(val kmPerSecond: Double) {

  val isKnown: Boolean
    get() = !kmPerSecond.isNaN()

  val kmPerHour: Double
    get() = if (isKnown) kmPerSecond * 3_600.0 else Double.NaN

  companion object {
    val UNKNOWN = Velocity(Double.NaN)
  }
}
