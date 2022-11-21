package com.easysay.payutils.inface

/**
 * Created By NiceStars on 2022/11/21
 */
interface PayResultListener {

    fun onPaySuccess()
    fun onPayError()
    fun onPayCancel()
}