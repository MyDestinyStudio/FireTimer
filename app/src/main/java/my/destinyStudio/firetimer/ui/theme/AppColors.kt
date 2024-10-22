package my.destinyStudio.firetimer.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    val mGray = Color(0xFF969494)
    val mLightGray= Color(0xFFB3B3B3)
     //val mLightGr   = Color(0xFFFFF4F4)
    val mBlueL= Color(0xFF17AFF3)
   // val mBlueLl= Color(0xFF73D2FD)
   // val mBlue = Color(0xFF4040E0)
    val mOrange= Color(0xFFFF541E)
   // val mOrangeL= Color(0xFFFF7042)
  //  val mOrangeLl = Color(0xFFF59576)
  //  val mYellow= Color(0xFFFFEE57)
    val mGreen= Color(0xFFA9EEAC)
  // val mG =Color(0xFFAA20C2)
 //   val mG3 =Color(0xFF673AB7)
    ////
    val themeBlue =Color( 0, 90, 143)
    val themeDeepCerulean =Color( 0, 131, 179)
    val themeCerulean =Color( 0, 168, 224)
    val themeFlamingo =Color( 240, 86, 56)
    val themeCarroteOrange =Color( 240, 151, 25)

    ///
    val colorOrangeRed = Color(0xFFfc5e02)

    //  #fa9e03 - Orange
    val colorOrange = Color(0xFFfa9e03)

    //  #fddb6d - Yellow
    val colorYellow = Color(0xFFfddb6d)

    //  #1cbfd8 - Light Blue
    val colorLightBlue = Color(0xFF1cbfd8)
    //
    val colorLightBlueL = Color(0xFF61E0F3)

    //  #1d7ed4 - Blue
    val colorBlue = Color(0xFF1d7ed4)

    //  #195eca - Dark Blue
    val colorDarkBlue = Color(0xFF195eca)

}
object IntervalTypeColors{

    val workoutColor = listOf ( AppColors.colorOrangeRed,AppColors.colorOrange)
    val restColor = listOf ( AppColors.colorDarkBlue,AppColors.colorBlue )
    val activeColor = listOf(AppColors.colorOrange, AppColors.colorYellow)
    val restBteSetsUpColor = listOf(AppColors.colorBlue, AppColors.colorLightBlue)
    val coolDownColor = listOf (AppColors.colorLightBlue,AppColors.colorLightBlueL)
    val warmUpColor = listOf (AppColors.colorYellow,AppColors.colorOrange )
    val otherColor = listOf (AppColors.colorYellow,AppColors.mGreen )

}