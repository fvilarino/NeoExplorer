package com.francesc.neoexplorer.data.preferences.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.francesc.neoexplorer.data.preferences.AppTheme
import com.francesc.neoexplorer.data.preferences.impl.proto.AppPreferencesProto
import com.francesc.neoexplorer.data.preferences.impl.proto.AppThemeProto
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class AppPreferencesRepositoryImplTest {

  @get:Rule val tmpFolder = TemporaryFolder()

  private lateinit var testScope: TestScope
  private lateinit var dataStore: DataStore<AppPreferencesProto>
  private lateinit var repository: AppPreferencesRepositoryImpl

  @Before
  fun setup() {
    testScope = TestScope(StandardTestDispatcher())
    dataStore =
      DataStoreFactory.create(
        serializer = AppPreferencesSerializer,
        scope = testScope,
        produceFile = { File(tmpFolder.newFolder(), "app_preferences.pb") },
      )
    repository = AppPreferencesRepositoryImpl(dataStore)
  }

  @Test
  fun `preferences flow initially emits default values`() = testScope.runTest {
    val initialPrefs = repository.preferences.first()
    assertEquals(AppTheme.AUTO, initialPrefs.theme)
    assertFalse(initialPrefs.useDynamicTheme)
  }

  @Test
  fun `setTheme updates the theme preference`() = testScope.runTest {
    repository.setTheme(AppTheme.DARK)

    val updatedPrefs = repository.preferences.first()
    assertEquals(AppTheme.DARK, updatedPrefs.theme)

    // Verify it's actually in DataStore
    val proto = dataStore.data.first()
    assertEquals(AppThemeProto.APP_THEME_PROTO_DARK, proto.theme)
  }

  @Test
  fun `setUseDynamicTheme updates the dynamic theme preference`() = testScope.runTest {
    repository.setUseDynamicTheme(true)

    val updatedPrefs = repository.preferences.first()
    assertTrue(updatedPrefs.useDynamicTheme)

    // Verify it's actually in DataStore
    val proto = dataStore.data.first()
    assertTrue(proto.useDynamicTheme)
  }

  // region Mapper Tests
  @Test
  fun `AppTheme toProto maps correctly`() {
    assertEquals(AppThemeProto.APP_THEME_PROTO_AUTO, AppTheme.AUTO.toProto())
    assertEquals(AppThemeProto.APP_THEME_PROTO_LIGHT, AppTheme.LIGHT.toProto())
    assertEquals(AppThemeProto.APP_THEME_PROTO_DARK, AppTheme.DARK.toProto())
  }

  @Test
  fun `AppThemeProto toDomain maps correctly`() {
    assertEquals(AppTheme.AUTO, AppThemeProto.APP_THEME_PROTO_AUTO.toDomain())
    assertEquals(AppTheme.LIGHT, AppThemeProto.APP_THEME_PROTO_LIGHT.toDomain())
    assertEquals(AppTheme.DARK, AppThemeProto.APP_THEME_PROTO_DARK.toDomain())
    assertEquals(AppTheme.AUTO, AppThemeProto.UNRECOGNIZED.toDomain())
  }

  @Test
  fun `AppPreferencesProto toDomain maps correctly`() {
    val proto =
      AppPreferencesProto.newBuilder()
        .setTheme(AppThemeProto.APP_THEME_PROTO_DARK)
        .setUseDynamicTheme(true)
        .build()

    val domain = proto.toDomain()

    assertEquals(AppTheme.DARK, domain.theme)
    assertTrue(domain.useDynamicTheme)
  }
  // endregion
}
