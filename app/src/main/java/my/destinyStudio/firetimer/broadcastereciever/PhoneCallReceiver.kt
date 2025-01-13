package my.destinyStudio.firetimer.broadcastereciever

//
//class PhoneCallStateHelper{
//    companion object {
//
//
//    }
//}
//
//
//
//class PhoneCallReceiver : BroadcastReceiver() {
//
//
//
//
//
//    override fun onReceive(context: Context, intent: Intent) {
//
//        Log.d("IncomingCall", "onReceive is called")
//
//
//        try {
//            if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
//                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
//
//                when (state) {
//                    TelephonyManager.EXTRA_STATE_RINGING -> {
//
//
//                        Log.d("IncomingCall", "Incoming call from: ")
//
//                    }
//
//                    TelephonyManager.EXTRA_STATE_OFFHOOK -> {
//
//
//
//                        Log.d("IncomingCall", "Call answered")
//
//                    }
//
//                    TelephonyManager.EXTRA_STATE_IDLE -> {
//
//
//
//                        Log.d("IncomingCall", "Call ended or missed")
//
//
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("IncomingCall", "Error processing incoming call", e)
//        }
//    }
//
//
//
//}