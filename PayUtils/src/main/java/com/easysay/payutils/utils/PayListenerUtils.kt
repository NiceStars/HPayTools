package com.easysay.payutils.utils

import com.easysay.payutils.inface.PayResultListener

/**
 * Created By NiceStars on 2022/11/21
 */
/**
 * Created By NiceStars on 2022/11/10
 * 监听装置
 */
object PayListenerUtils {

    private var resultList: MutableList<PayResultListener> = ArrayList()

    fun addListener(listener: PayResultListener) {
        if (!resultList.contains(listener)) {
            resultList.add(listener)
        }
    }

    fun removeListener(listener: PayResultListener) {
        if (resultList.contains(listener)) {
            resultList.remove(listener)
        }
    }

    fun addSuccess() {
        for (listener in resultList) {
            listener.onPaySuccess()
        }
    }

    fun addCancel() {
        for (listener in resultList) {
            listener.onPayCancel()
        }
    }

    fun addError() {
        for (listener in resultList) {
            listener.onPayError()
        }
    }

}