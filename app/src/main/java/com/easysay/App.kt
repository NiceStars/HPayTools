package com.easysay

import android.app.Application
import com.easysay.payutils.manager.PaymentManager

/**
 * Created By NiceStars on 2022/11/21
 */
class App : Application() {

    private val wxId = "wxa12d75a559a4cf72"
    private val partnerId = "1338737101"  //固定商户号

    override fun onCreate() {
        super.onCreate()
        initSDK()
    }


    fun initSDK() {
        PaymentManager.initPay(wxId, partnerId)

    }
}