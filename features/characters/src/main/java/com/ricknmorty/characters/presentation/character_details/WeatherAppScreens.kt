package com.ricknmorty.characters.presentation.character_details

import android.util.Log
import android.widget.Space
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.ricknmorty.characters.R

/**
 * Created by Tejas Deshmukh on 09/07/25.
 */

@Composable
@Preview
fun HomeScreen() {
    HomeScreenBackgroundContainer()
}

@Composable

fun HomeScreenBackgroundContainer() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.home_screen_background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(alignment = BiasAlignment(horizontalBias = 0f, verticalBias = 0.2f)),
            painter = painterResource(id = R.drawable.home_image),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        WeatherInfoBottomSheetScaffold()
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WeatherInfoBottomSheetScaffold() {
    val sheetState = rememberBottomSheetScaffoldState()
    val dragValue by remember(sheetState.bottomSheetState.currentValue) {
        derivedStateOf { sheetState.bottomSheetState.currentValue }
    }

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    val textSize = animateFloatAsState(targetValue = 60.dp.value - (sheetState.bottomSheetState.progress(
        BottomSheetValue.Collapsed, BottomSheetValue.Expanded) * 40.dp.value), label = "text_size")

    val topPadding = animateFloatAsState(targetValue = 50.dp.value - (sheetState.bottomSheetState.progress(
        BottomSheetValue.Collapsed, BottomSheetValue.Expanded) * 30.dp.value), label = "padding_top")

    val offset = animateFloatAsState(targetValue = 68.dp.value - (sheetState.bottomSheetState.progress(
        BottomSheetValue.Collapsed, BottomSheetValue.Expanded) * 68.dp.value), label = "padding_top")

    val imageOffset = animateFloatAsState(targetValue = 0.dp.value - (sheetState.bottomSheetState.progress(
        BottomSheetValue.Collapsed, BottomSheetValue.Expanded) * screenHeight.value), label = "padding_top")

    val offsetStart = animateFloatAsState(targetValue = 0.dp.value + (sheetState.bottomSheetState.progress(
        BottomSheetValue.Collapsed, BottomSheetValue.Expanded) * 50.dp.value), label = "padding_top")

    val fadeAlpha = animateFloatAsState(
        targetValue = 1f - sheetState.bottomSheetState.progress(
            BottomSheetValue.Collapsed, BottomSheetValue.Expanded
        ),
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "fade_alpha"
    )


    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color(
            red = 0f,
            green = 0f,
            blue = 0f,
            alpha = 0f
        ),
        scaffoldState = sheetState,
        sheetGesturesEnabled = true,
        sheetPeekHeight = 500.dp,
        topBar = {
            AnimatedVisibility(
                visible = true,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
//                androidx.compose.material.TopAppBar(
//                    title = { Text("Weather Details") },
//                    backgroundColor = MaterialTheme.colors.primary,
//                    modifier = Modifier.animateContentSize().fillMaxWidth().height(350.dp)
//                )

//                Box(modifier = Modifier.animateContentSize().fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
//                    Text("Weather Details")
//                }
            }
        },
        sheetContent = {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 100.dp)) {
            Column(modifier = Modifier) {

                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color("#2E335A".toColorInt()).copy(alpha = 0.98f),
                                Color("#1C1B33".toColorInt()).copy(alpha = 0.98f)
                            ),
                            startX = 0f,
                            endX = 300f,
                        )
                    )) {

                    item {
                        ForecastTabs()
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(all = 12.dp)
                        ) {
                            items(8) {
                                ForecastCard()
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        AirQualityCard()

                        Spacer(modifier = Modifier.height(16.dp))

                        OtherInfoCard()

                        Spacer(modifier = Modifier.height(16.dp))

                        OtherInfoCard()

                        Spacer(modifier = Modifier.height(16.dp))

//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .background(color = MaterialTheme.colors.secondaryVariant)
//                        ) {
//
//                        }
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .background(color = MaterialTheme.colors.onSurface)
//                        ) {
//
//                        }
//
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .background(color = MaterialTheme.colors.error)
//                        ) {
//
//                        }
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .background(color = MaterialTheme.colors.onError)
//                        ) {
//
//                        }
//
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .background(color = MaterialTheme.colors.secondaryVariant)
//                        ) {
//
//                        }
                    }

//                    item {
//                        LazyVerticalGrid(
//                            columns = GridCells.Fixed(2), // Defines a fixed number of 2 columns
//                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
//                            contentPadding = PaddingValues(8.dp), // Optional padding around the grid
//                            verticalArrangement = Arrangement.spacedBy(8.dp), // Optional spacing between rows
//                            horizontalArrangement = Arrangement.spacedBy(8.dp) // Optional spacing between columns
//                        ) {
//                            items(8) { itemNumber ->
//                                OtherInfoCard()
//                            }
//                        }
//                    }

                }
            }

        }
    }) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(color = "#312B5B".toColorInt()))) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = imageOffset.value.dp),
                painter = painterResource(id = R.drawable.home_screen_background),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .offset(y = imageOffset.value.dp)
                    .align(alignment = BiasAlignment(horizontalBias = 0f, verticalBias = 0.2f)),
                painter = painterResource(id = R.drawable.home_image),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )


            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(topPadding.value.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Montreal", fontSize = 28.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "19°",
                        modifier = Modifier.offset(x = -offsetStart.value.dp),
                        fontSize = textSize.value.sp,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.surface
                    )
                    Text("Mostly Clear", modifier = Modifier.padding(top = offset.value.dp, start = offsetStart.value.dp), color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "H: 24°",
                        fontSize = 18.sp,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.surface.copy(alpha = fadeAlpha.value)
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(modifier = Modifier.wrapContentSize(), text = "L: 18°", fontSize = 18.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.surface.copy(alpha = fadeAlpha.value))
                }
            }


        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ForecastTabs() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Hourly Forecast", "Weekly Forecast")
    
    androidx.compose.material3.TabRow(
        modifier = Modifier.background(Color(
            red = 0f,
            green = 0f,
            blue = 0f,
            alpha = 0f
        )),
        selectedTabIndex = selectedTab,
        containerColor = Color(
            red = 0f,
            green = 0f,
            blue = 0f,
            alpha = 0f
        ),
        contentColor = Color("#EBEBF5".toColorInt())
//        indicator = { tabPositions -> androidx.compose.material3.TabRowDefaults.PrimaryIndicator(
//            modifier = Modifier.tabRowIndicatorOffset(
//                tabPositions[selectedTab],
//                density = LocalDensity.current,
//                tabRowWidth =Modifier.fillMaxWidth()
//            ),
//            color = MaterialTheme.colors.primary
//        )}
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(modifier = Modifier.background(Color(
                red = 0f,
                green = 0f,
                blue = 0f,
                alpha = 0f
            )),
                text = { Text(text = title, fontSize = 16.sp) },
                selected = selectedTab == index,
                onClick = { selectedTab = index }
            )
        }
    }
}


@Composable
fun ForecastCard() {
    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF48319D)),
        border = BorderStroke(1.dp, Color(0xFF6C4ACE)),
        shape = RoundedCornerShape(percent = 50),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("12 AM", color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_rainy),
                contentDescription = "Cloudy",
                modifier = Modifier.size(40.dp)
            )
            Text("30%", color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
            Spacer(modifier = Modifier.height(12.dp))
            Text("12°", color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
        }
    }
}

@Composable
fun AirQualityCard() {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1D47)),
        border = BorderStroke(1.dp, Color(0xFF6C4ACE)),
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {

    }
}



@Composable
fun OtherInfoCard() {
    Row(modifier = Modifier
        .fillMaxWidth().wrapContentHeight().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedCard(
            modifier = Modifier
                .weight(1F)
                .aspectRatio(1F),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1D47)),
            border = BorderStroke(1.dp, Color(0xFF6C4ACE)),
            shape = RoundedCornerShape(size = 16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
        ) {

        }

        OutlinedCard(
            modifier = Modifier
                .weight(1F)
                .aspectRatio(1F),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1D47)),
            border = BorderStroke(1.dp, Color(0xFF6C4ACE)),
            shape = RoundedCornerShape(size = 16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
        ) {

        }
    }
}

//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun WeatherInfoBottomSheet() {
//    var isSheetExpanded by remember { mutableStateOf(false) }
//    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = false,
//        confirmValueChange = { newValue ->
//            // Prevent transitions to Hidden or Expanded states, keep only PartiallyExpanded
//            Log.d("Sheet state changed to", "$newValue")
//            isSheetExpanded = newValue == SheetValue.Expanded
//            newValue == SheetValue.PartiallyExpanded || newValue == SheetValue.Expanded
//        },
//    )
//    val properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false)
//    LaunchedEffect(Unit) {
//        sheetState.expand()
//    }
//    ModalBottomSheet(
//        // Minimum expanded height is controlled by the height of the content
//        modifier = Modifier.wrapContentHeight(),
//        onDismissRequest = {},
//        sheetState = sheetState,
//        properties = properties,
//        content = {
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()) {
//                Column {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(0.2F)
//                            .background(color = MaterialTheme.colors.secondary)
//                    ) {
//
//                    }
//
//                    Column(modifier = Modifier
//                        .fillMaxWidth().wrapContentHeight()) {
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(0.3F)
//                                .background(color = MaterialTheme.colors.onSecondary)
//                        ) {
//
//                        }
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(0.3F)
//                                .background(color = MaterialTheme.colors.secondary)
//                        ) {
//
//                        }
//
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(0.3F)
//                                .background(color = MaterialTheme.colors.secondary)
//                        ) {
//
//                        }
//
//                    }
//                }
//
//            }
//        }
//    )
//}