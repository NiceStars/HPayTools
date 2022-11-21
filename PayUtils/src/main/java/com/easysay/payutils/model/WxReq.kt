package com.easysay.payutils.model

data class WxReq(
    var appId: String,
    var nonceStr: String,
    var orderId: String,
    var paySign: String,
    var prepayId: String,
    var signType: String,
    var timeStamp: String
)