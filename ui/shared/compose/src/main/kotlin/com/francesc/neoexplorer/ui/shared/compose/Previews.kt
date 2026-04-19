package com.francesc.neoexplorer.ui.shared.compose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/** Preview configuration for a simple widget / component (light + dark). */
@Preview(widthDp = 360, showBackground = true, name = "Light")
@Preview(widthDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark")
annotation class WidgetPreviews

/** Preview configuration for a phone screen (light + dark). */
@Preview(device = Devices.PIXEL_7, showBackground = true, name = "Light", group = "Phone")
@Preview(
    device = Devices.PIXEL_7,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark",
    group = "Phone",
)
annotation class PhonePreviews

/** Preview configuration for a phone in landscape (light + dark). */
@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape",
    showBackground = true,
    name = "Light",
    group = "Phone Landscape",
)
@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark",
    group = "Phone Landscape",
)
annotation class LandscapePhonePreviews

/** Preview configuration for a tablet (light + dark). */
@Preview(device = Devices.PIXEL_TABLET, showBackground = true, name = "Light", group = "Tablet")
@Preview(
    device = Devices.PIXEL_TABLET,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark",
    group = "Tablet",
)
annotation class TabletPreviews

