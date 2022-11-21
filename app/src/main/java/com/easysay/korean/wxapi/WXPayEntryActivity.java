package com.easysay.korean.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.easysay.payutils.utils.PayListenerUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created By NiceStars on 2022/11/8
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    private String TAG = WXPayEntryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this,"wxa12d75a559a4cf72");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i(TAG, "WX code:------>" + baseResp.errCode);
        switch (baseResp.errCode) {
            case 0:
                //成功
                PayListenerUtils.INSTANCE.addSuccess();
                break;
            case -1:
                //失败
                PayListenerUtils.INSTANCE.addError();
                break;
            case -2:
                //用户取消
                PayListenerUtils.INSTANCE.addCancel();
                break;
        }
        finish();

    }
}
