package com.easysay.payutils.manager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.alipay.sdk.app.PayTask
import com.easysay.payutils.model.PayResult
import com.easysay.payutils.model.WxReq
import com.easysay.payutils.utils.PayListenerUtils
import com.google.gson.Gson
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory


/**
 * Created By NiceStars on 2022/11/21
 */

object PaymentManager {

    private val TAG = PaymentManager.javaClass.simpleName
    private const val SDK_PAY_FLAG = 1001

    private var payPlatform = "wx"//支付渠道
    private var wxId = ""
    private var partnerId = ""  //固定商户号
    private const val packageValue = "Sign=WXPay"  //固定值

    fun initPay(wxId: String, partnerId: String) {
        this.wxId = wxId
        this.partnerId = partnerId
    }


    fun getParams(): String {
        return StringBuilder().run {
            append("wxId=$wxId")
            append("partnerId=$partnerId")
            toString()
        }

    }

    fun getCurrentPayChannel(): String {
        return payPlatform
    }


    fun payWechat(context: Context, result: String?) {
        payPlatform = "wx"
        val dataObject = JSONObject.parseObject(result)
        val charge: String = dataObject.getString("wx")
        val api = WXAPIFactory.createWXAPI(context, wxId)
        Log.e(TAG, wxId)
        api.registerApp(wxId)
        val (appId, nonceStr, _, paySign, prepayId, _, timeStamp) = Gson().fromJson(charge,
            WxReq::class.java)
        val req = PayReq()
        req.appId = appId //appId
        req.partnerId = partnerId //固定商户号
        req.prepayId = prepayId //预支付订单号
        req.nonceStr = nonceStr //秘钥文件
        req.timeStamp = timeStamp //时间戳
        req.sign = paySign //签名文件
        req.packageValue = packageValue //固定值
        api.sendReq(req) //这里就发起调用微信支付了
    }

    fun payAliPay(activity: Activity, result: String?) {
        payPlatform = "alipay"
        val dataObject = JSONObject.parseObject(result)
        val charge: String = dataObject.getString("alipay")
        val payRunnable = Runnable {
            val alipay = PayTask(activity)
            val result = alipay.payV2(charge, true)
            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }
        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }


    /**
     * 支付宝状态
     * 9000 订单支付成功
     * 8000 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 4000 订单支付失败
     * 5000 重复请求
     * 6001 用户中途取消
     * 6002 网络连接出错
     * 6004 支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 其它   其它支付错误
     */
    private val mHandler: Handler = object : Handler() {
        @SuppressLint("HandlerLeak")
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String?, String?>)

                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo: String = payResult.getResult() // 同步返回需要验证的信息
                    val resultStatus: String = payResult.getResultStatus()
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        PayListenerUtils.addSuccess()
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        PayListenerUtils.addCancel()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        PayListenerUtils.addError()
                    }
                }
            }
        }
    }


}