package com.francesc.neoexplorer.ui.feature.home.di

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.NavDecoration
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.ExperimentalCircuitApi
import com.slack.circuit.runtime.navigation.NavArgument
import com.slack.circuit.runtime.navigation.NavStackList
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
interface CircuitModule {

    @Multibinds
    fun presenterFactories(): Set<Presenter.Factory>

    @Multibinds
    fun uiFactories(): Set<Ui.Factory>

    @Provides
    @SingleIn(AppScope::class)
    fun provideCircuit(
        presenterFactories: @JvmSuppressWildcards Set<Presenter.Factory>,
        uiFactories: @JvmSuppressWildcards Set<Ui.Factory>,
    ): Circuit = Circuit.Builder()
        .addPresenterFactories(presenterFactories)
        .addUiFactories(uiFactories)
        .setDefaultNavDecoration(NavigationDecoration)
        .build()
}

private object NavigationDecoration : NavDecoration {

    @OptIn(ExperimentalCircuitApi::class)
    @Composable
    override fun <T : NavArgument> DecoratedContent(
        args: NavStackList<T>,
        modifier: Modifier,
        content: @Composable (T) -> Unit,
    ) {
        var prevBackstackDepth by rememberSaveable { mutableIntStateOf(0) }
        val backStackDepth = args.backwardItems.iterator().asSequence().count()

        AnimatedContent(
            targetState = args,
            modifier = modifier,
            transitionSpec = {
                val signum = if (prevBackstackDepth < backStackDepth) 1 else -1
                (slideInHorizontally { signum * it } + fadeIn() togetherWith
                    slideOutHorizontally { -signum * it } + fadeOut())
                    .using(SizeTransform(clip = false))
            },
            label = "navigation",
        ) {
            content(it.first())
        }
        prevBackstackDepth = backStackDepth
    }
}

