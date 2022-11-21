package com.easysay.korean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.easysay.korean.databinding.ActivityMainBinding
import com.easysay.payutils.inface.PayResultListener
import com.easysay.payutils.manager.PaymentManager
import com.easysay.payutils.utils.PayListenerUtils

lateinit var binding: ActivityMainBinding
lateinit var activity: FragmentActivity

class MainActivity : AppCompatActivity(), PayResultListener {
    private var TAG = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PayListenerUtils.addListener(this)
        activity = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.handler = Handler()

    }


    val aliPayString =
        "{\"alipay\":\"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2021002175682894&biz_content=%7B%22out_trade_no%22%3A%22usc16690222508240427959%22%2C%22total_amount%22%3A8.99%2C%22subject%22%3A%22%E9%9F%A9%E8%AF%AD200U%E9%92%BB%22%2C%22body%22%3A%22%7B%5C%22hcAppId%5C%22%3A%5C%22korean%5C%22%2C%5C%22payChannel%5C%22%3A%5C%22app%5C%22%2C%5C%22userId%5C%22%3A%5C%22kid8df1b1c20af1104fa70b474081d7ec070366%5C%22%2C%5C%22platform%5C%22%3A%5C%22android-%5C%22%7D%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fapi.hcreator.cn%2FwapsPay%2FjyPay%2FalipayPayNotify.do&sign=Rg7f5emRBE%2BviZ9i9TTrh%2FgWzNgxl8J3HkG%2FqvFK1rasPUEEQ3Nl1q3YxoyPSk5%2BtuujXpEaIxeupz%2B%2FyHwJwoMWOsY44UUu1mRpNAzF82KlkrexTdgLHWkHVlR3KxMCCCAMEkR%2FLirPGKiN1fwddX8CnC%2BR6KvbTkDx%2BDE%2FSlST81Oju4FrvQSn497iiplMN9Pt6VMdiuy6cC7R5FYk96zvpdcJSrqMSkg6TOKeA3oA2KrwZYyRRkyDz6bgKuTnQbC6H39dWKf7FCG0%2BB5lfLAA8fZLfdUpUv6pzwYxkhEoEOC3ruT%2BJxNJWljRdbroPto0996PlIe3lu%2FWaxKQiA%3D%3D&sign_type=RSA2&timestamp=2022-11-21+17%3A17%3A30&version=1.0\"}"
    val wxPayString =
        "{\"wx\":{\"timeStamp\":\"1669022347832\",\"paySign\":\"Ia6EjFkG9aoAgrLryOkuwS/e0QAZQFSdKsPHhiwPLKOftdbdR+5/UviiR67lsmaSGXbLL384CWbG0Es4v1ONVoAXb7X4W6YHMFuGH3czRrfqROrAQyuxrtqBDUvPpUSQ/qA7B66+7ifGx1KSz0Uh9pQGRi55sMdYgrohKiaQQfzT7V1kA9MhY1uKOxIRUpZss8rmdx3faB9lobQbrNt+GMGn5EuSCOmniB8tdrgaBksvo1EESUQ7OyNy9SVclmppnpsVx/a9VZ4kVkp359seyAaocQklg2zuzXQnhzQlHgRau4yZ71phZzhvyqIUnxJDo8oqLbXUNZ36KFysUDTlKw==\",\"orderId\":\"usc16690223473343176100\",\"appId\":\"wxa12d75a559a4cf72\",\"signType\":\"RSA\",\"prepayId\":\"wx21171907751365b4fb761e10e56a770000\",\"nonceStr\":\"jju021nlr2gd849yv9yqt5pypq5x9hox\"}}"

    inner class Handler {

        fun starWeChatPay() {
            PaymentManager.payWechat(activity, wxPayString)
        }

        fun starAliPay() {
            PaymentManager.payAliPay(activity, aliPayString)
        }

        fun getCurrentPayChannel() {
           Toast.makeText(activity,PaymentManager.getCurrentPayChannel(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaySuccess() {

    }

    override fun onPayError() {

    }

    override fun onPayCancel() {

    }

    override fun onDestroy() {
        super.onDestroy()
        PayListenerUtils.removeListener(this)
    }


}