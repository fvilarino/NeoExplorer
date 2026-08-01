package com.francesc.neoexplorer.data.neo.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlin.time.Clock

@BindingContainer
@ContributesTo(AppScope::class)
object NeoDataModule {
  @Provides fun provideClock(): Clock = Clock.System

  @Provides fun provideAsteroidLocalDataSource(cache: NeoCache): AsteroidLocalDataSource = cache

  @Provides fun provideFeedLocalDataSource(cache: NeoCache): FeedLocalDataSource = cache
}
