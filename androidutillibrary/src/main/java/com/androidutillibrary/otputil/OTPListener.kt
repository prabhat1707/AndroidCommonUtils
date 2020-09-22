package com.androidutillibrary.otputil

interface OTPListener {
    /**
     * Callback Fired when user starts typing in the OTP/PIN box.
     */
    fun onUserInteraction()

    /**
     * @param otp Filled OTP
     * Callback Fired when user has completed filling the OTP/PIN.
     */
    fun onOTPComplete(otp: String)
}
