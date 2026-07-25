package com.francesc.neoexplorer.ui.shared.asteroid

/** Intermediate result of parsing a [com.francesc.neoexplorer.data.neo.model.NeoFeed]. */
data class ParsedFeed(
  val asteroids: List<AsteroidUiModel>,
  val hazardousCount: Int,
)
