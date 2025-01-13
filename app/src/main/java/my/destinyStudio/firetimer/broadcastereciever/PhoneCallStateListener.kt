package my.destinyStudio.firetimer.broadcastereciever

//
//@Suppress("DEPRECATION")
//class PhoneCallStateListener(private val context: Context) {
//
//
//    private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//
//    fun phoneStateFlow(): Flow<Int> = callbackFlow {
//
//        val callback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            object : TelephonyCallback(), TelephonyCallback.CallStateListener {
//                override fun onCallStateChanged(state: Int) {
//                    trySend(state)
//                }
//            }
//        } else {
//            object: PhoneStateListener() {
//                @Deprecated("Deprecated in Java", ReplaceWith("trySend(state)"))
//                override fun onCallStateChanged(state: Int, phoneNumber: String?) {
//                    trySend(state)
//                }
//            }
//        }
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            telephonyManager.registerTelephonyCallback(context.mainExecutor, callback as TelephonyCallback)
//        } else {
//            telephonyManager.listen(callback as PhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
//        }
//
//
//        awaitClose {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                telephonyManager.unregisterTelephonyCallback(callback as TelephonyCallback)
//            } else {
//                telephonyManager.listen(callback as PhoneStateListener, PhoneStateListener.LISTEN_NONE)
//            }
//        }
//    }
//}
//









