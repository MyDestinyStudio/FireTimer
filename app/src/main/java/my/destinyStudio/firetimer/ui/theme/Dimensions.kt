package my.destinyStudio.firetimer.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class DimensApp(
    val appTitle: Int = 25  ,
    val appTitleSuffix: Int = 18  ,
    val appTitleInfo: Int = 20  ,
    val appTopBar: Dp = 70.dp ,
    val pickerCardSize:Dp =60.dp,
    val pickerCardBorderTickness:Dp =2.dp,
    val pickerCardSFontSize:Int  = 18 ,
    val labelFontSize:Int  = 20,
    val appNavigationBar: Dp = 70.dp ,
    val floatingActionButton: Dp = 70.dp,
    val cardWorkoutHeight: Dp = 180 .dp,
    val cardTitleText: Dp = 30.dp,
    val cardEditHeight: Dp = 150.dp,
    val timerIcons: Dp = 40.dp,
    val sliderCounterFont: Int = 25 ,
    val sliderCounterWidth: Dp = 100.dp,
    val bottomCard: Dp = 300.dp,
    val bottomCardFontSize :Int = 25,
    val triangleSize:Dp = 20.dp,
    val bottomCardIndicatorSize :Int = 25,
    val timerLeftText :Int = 35,
    val topAppEditBarHeight: Dp = 80.dp,
    val appEditCardHeight: Dp = 80.dp,
    val logoSize: Dp = 42.dp,
    val borderStroke: Dp =  1.dp,
    val cardStarHeight: Dp =  130.dp
)

val CompactSmallDimens =DimensApp(
    appTitle  = 20  ,
    appTitleSuffix  = 18  ,
     appTitleInfo  = 15  ,
   appNavigationBar  = 50.dp ,
      appTopBar  = 60.dp ,
  pickerCardSize  =50.dp,
  pickerCardSFontSize   = 15 ,
    pickerCardBorderTickness  = 2.dp,
  labelFontSize = 15 ,
  floatingActionButton  = 70.dp,
  cardWorkoutHeight  = 160 .dp,
  cardTitleText  = 15.dp,
  cardEditHeight  = 140.dp,
  timerIcons = 50.dp,
  sliderCounterWidth  = 60.dp,
  bottomCard  = 200.dp,
  bottomCardFontSize  = 20,
  triangleSize  = 15.dp,
  bottomCardIndicatorSize   = 20,
  timerLeftText   = 25,
  topAppEditBarHeight  = 60.dp,
  appEditCardHeight  = 60.dp,
 logoSize  = 42.dp
)


val CompactMediumDimens = DimensApp(
    appTitle  = 25  ,
    appTitleSuffix  = 20  ,
    appNavigationBar  = 70.dp ,
    appTitleInfo  = 18  ,
    appTopBar  = 60.dp ,
    pickerCardSize  =60.dp,
    pickerCardSFontSize   = 20,
    pickerCardBorderTickness  = 2.dp,
    labelFontSize = 18 ,
    floatingActionButton  = 70.dp,
    cardWorkoutHeight  = 180 .dp,
    cardTitleText  = 18.dp,
    cardEditHeight  = 150.dp,
    timerIcons = 50.dp,
    sliderCounterWidth  = 100.dp,
    bottomCard  = 300.dp,
    bottomCardFontSize  = 25,
    triangleSize  = 20.dp,
    bottomCardIndicatorSize   = 25,
    timerLeftText   = 35,
    topAppEditBarHeight  = 80.dp,
    appEditCardHeight  = 80.dp,
    logoSize  = 42.dp
)

val CompactDimens = DimensApp(
    appTitle  = 25  ,
    appTitleSuffix  = 20  ,
    appTitleInfo  = 20 ,
    appTopBar  = 60.dp ,
    appNavigationBar  = 70.dp ,
    pickerCardSize  =70.dp,
    pickerCardSFontSize   = 25,
    pickerCardBorderTickness  = 2.dp,
    labelFontSize = 20  ,
    floatingActionButton  = 70.dp,
    cardWorkoutHeight  = 180 .dp,
    cardTitleText  = 18.dp,
    cardEditHeight  = 150.dp,
    timerIcons = 50.dp,
    sliderCounterWidth  = 100.dp,
    sliderCounterFont=  45 ,
    bottomCard  = 300.dp,
    bottomCardFontSize  = 25,
    triangleSize  = 20.dp,
    bottomCardIndicatorSize   = 25,
    timerLeftText   = 35,
    topAppEditBarHeight  = 80.dp,
    appEditCardHeight  = 80.dp,
    logoSize  = 42.dp
)

val MediumDimens = DimensApp(
    appTitle  = 45  ,
     appTitleSuffix  = 30  ,
    appTopBar  = 75 .dp ,
    appNavigationBar  = 80.dp ,
    appTitleInfo  =25   ,
    pickerCardSize  =90.dp,
    pickerCardSFontSize   = 35  ,
    pickerCardBorderTickness  = 3.dp,
    labelFontSize = 25 ,

    floatingActionButton  = 90.dp,
    cardWorkoutHeight  = 190 .dp,
    cardTitleText  = 20.dp,
    cardEditHeight  = 160.dp,
    timerIcons = 120.dp,
    sliderCounterWidth  = 140.dp,
    sliderCounterFont=  65 ,
    bottomCard  = 450 .dp,
    bottomCardFontSize  = 35,
    triangleSize  = 40.dp,
    bottomCardIndicatorSize   = 45,
    timerLeftText   = 55,
    topAppEditBarHeight  = 90.dp,
    appEditCardHeight  = 90.dp,
    logoSize  = 42.dp
)

val ExpandedDimens = DimensApp(

    appTitle  = 55  ,
    appTopBar  = 85 .dp ,
    appNavigationBar  = 100 .dp ,
    appTitleInfo  = 35  ,
    pickerCardSize  =100.dp,
    pickerCardSFontSize   = 45,
    pickerCardBorderTickness  = 3.dp,
    labelFontSize = 30 ,
    floatingActionButton  = 100.dp,
    cardWorkoutHeight  = 200 .dp,
    cardTitleText  = 35.dp,
    cardEditHeight  = 200.dp,
    timerIcons =140.dp,
    sliderCounterWidth  = 180.dp,
    sliderCounterFont=  85 ,
    bottomCard  = 500.dp,
    bottomCardFontSize  = 35,
    triangleSize  = 30.dp,
    bottomCardIndicatorSize   = 35,
    timerLeftText   = 45,
    topAppEditBarHeight  = 100.dp,
    appEditCardHeight  = 100.dp,
    logoSize  = 42.dp
)