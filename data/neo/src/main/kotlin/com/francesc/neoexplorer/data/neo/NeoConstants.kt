package com.francesc.neoexplorer.data.neo

/** Constants and business rules for the NASA NeoWs (Near Earth Object Web Service) API. */
object NeoConstants {
  /**
   * The maximum number of days allowed in a single feed request.
   *
   * The NeoWs `feed` endpoint caps at 7 days (inclusive). Requests exceeding this range will be
   * rejected by the server.
   */
  const val MAX_FEED_RANGE_DAYS = 7

  /** The default page size for the `browse` endpoint. */
  const val DEFAULT_PAGE_SIZE = 20

  /** The starting page index for paginated requests (0-indexed). */
  const val STARTING_PAGE_INDEX = 0
}
