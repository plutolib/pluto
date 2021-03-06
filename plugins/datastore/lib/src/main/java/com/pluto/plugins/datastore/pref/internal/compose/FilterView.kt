package com.pluto.plugins.datastore.pref.internal.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.Insets
import com.pluto.plugins.datastore.pref.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

const val ColumnWidthPercentage = .8f

@Composable
internal fun FilterView(
    showFilterState: MutableStateFlow<Boolean>,
    filterState: MutableStateFlow<Map<String, Boolean>>,
    insets: MutableState<Insets>
) {
    val visibleState = remember { MutableTransitionState(false) }
    visibleState.targetState = showFilterState.collectAsState().value
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        FilterBackground(visibleState, showFilterState)
        FilterItem(
            visibleState, filterState,
            Modifier
                .padding(
                    top = with(LocalDensity.current) {
                        insets.value.top.toDp()
                    } + 16.dp,
                    end = 12.dp
                )
                .align(Alignment.TopEnd)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressWarnings("LongMethod")
@Composable
private fun FilterItem(
    visibleState: MutableTransitionState<Boolean>,
    filterState: MutableStateFlow<Map<String, Boolean>>,
    modifier: Modifier,
) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = expandIn(expandFrom = Alignment.TopEnd),
        exit = shrinkOut(shrinkTowards = Alignment.TopEnd),
        modifier = modifier
            .wrapContentSize()
    ) {
        Column(
            Modifier
                .borderBackground(
                    bgColor = colorResource(id = R.color.pluto___white),
                    borderColor = colorResource(id = R.color.pluto___white),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(bottom = 4.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth(ColumnWidthPercentage)
                    .borderBackground(
                        bgColor = colorResource(id = R.color.pluto___section_color),
                        borderColor = colorResource(id = R.color.pluto___section_color),
                        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Preferences",
                    color = colorResource(id = R.color.pluto___text_dark_80),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.muli)),
                        fontSize = 15.sp
                    )
                )
            }
            filterState.collectAsState().value.entries.forEachIndexed { index, entry ->
                Column(
                    Modifier
                        .clickable {
                            filterState.update { srcMap ->
                                mutableMapOf<String, Boolean>().also {
                                    it.putAll(srcMap)
                                    it[entry.key] = !(it[entry.key] ?: true)
                                }
                            }
                        }
                        .fillMaxWidth(ColumnWidthPercentage)
                ) {
                    if (index != 0) {
                        Divider(color = colorResource(id = R.color.pluto___dark_05))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Checkbox(
                            checked = entry.value,
                            onCheckedChange = { isChecked ->
                                filterState.update { srcMap ->
                                    mutableMapOf<String, Boolean>().also {
                                        it.putAll(srcMap)
                                        it[entry.key] = isChecked
                                    }
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorResource(id = R.color.pluto___blue),
                                uncheckedColor = colorResource(id = R.color.pluto___dark_40)
                            )
                        )
                        Text(
                            text = entry.key,
                            color = colorResource(id = R.color.pluto___text_dark_80),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.muli_semibold)),
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun Modifier.borderBackground(
    bgColor: Color,
    borderColor: Color,
    shape: Shape = RectangleShape,
): Modifier {
    return background(
        color = bgColor,
        shape = shape
    ).border(
        width = 1.dp,
        color = borderColor,
        shape = shape
    )
}

@Composable
private fun FilterBackground(
    visibleState: MutableTransitionState<Boolean>,
    showFilterState: MutableStateFlow<Boolean>
) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(colorResource(id = R.color.pluto___dark_80))
                .clickable {
                    showFilterState.update {
                        false
                    }
                }
        )
    }
}
