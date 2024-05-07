package my.destinyStudio.firetimer.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    val mGray = Color(0xFF969494)
    val mLightGray= Color(0xFFB3B3B3)
    val mLightGr   = Color(0xFFEBE9E9)
    val mBlueL= Color(0xFF17AFF3)
    val mBlueLl= Color(0xFF73D2FD)
    val mBlue = Color(0xFF3245AF)
    val mOrange= Color(0xFFFF541E)
    val mOrangeL= Color(0xFFFF7042)
    val mOrangeLl = Color(0xFFFC9473)
    val mYellow= Color(0xFFFFEE57)
    val mGreen= Color(0xFF88F08C)

}
object IntervalTypeColors{

    val warmUpColor = listOf (AppColors.mBlueL,AppColors.mLightGr)
    val workoutColor = listOf (AppColors.mOrange,AppColors.mOrangeL)
    val restColor = listOf (AppColors.mBlue,AppColors.mBlueL)
    val activeColor = listOf (AppColors.mBlueLl,AppColors.mOrangeLl)
    val restBteSetsUpColor = listOf (AppColors.mBlueLl,AppColors.mGray)
    val coolDownColor = listOf (AppColors.mBlueLl,AppColors.mLightGr)
    val otherColor = listOf (AppColors.mYellow,AppColors.mGreen)

}