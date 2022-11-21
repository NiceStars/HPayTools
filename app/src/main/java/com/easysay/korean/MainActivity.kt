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




    inner class Handler {

        fun starWeChatPay() {
            PaymentManager.payWechat(activity, "")
        }

        fun starAliPay() {
            PaymentManager.payAliPay(activity, "")
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