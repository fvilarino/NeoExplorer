package com.francesc.neoexplorer.ui.shared.formatter.impl

import java.util.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SystemDateFormatterTest {

  private val formatter = SystemDateFormatter()
  private var originalLocale: Locale? = null

  @Before
  fun setup() {
    originalLocale = Locale.getDefault()
  }

  @After
  fun tearDown() {
    originalLocale?.let { Locale.setDefault(it) }
  }

  @Test
  fun `format correctly formats a date in US locale`() {
    Locale.setDefault(Locale.US)
    val date = LocalDate(2026, Month.JULY, 25)
    val result = formatter.format(date)
    assertEquals("25 Jul 2026", result)
  }

  @Test
  fun `format correctly formats a date in Spanish locale`() {
    Locale.setDefault(Locale.forLanguageTag("es-ES"))
    val date = LocalDate(2026, Month.JULY, 25)
    val result = formatter.format(date)
    assertEquals("25 jul 2026", result)
  }

  @Test
  fun `format correctly formats a date in French locale`() {
    Locale.setDefault(Locale.FRANCE)
    val date = LocalDate(2026, Month.JULY, 25)
    val result = formatter.format(date)
    // French short month for July is "juil."
    assertEquals("25 juil. 2026", result)
  }

  @Test
  fun `format correctly formats a date with single digit day`() {
    Locale.setDefault(Locale.US)
    val date = LocalDate(2026, Month.JANUARY, 5)
    val result = formatter.format(date)
    assertEquals("5 Jan 2026", result)
  }
}
