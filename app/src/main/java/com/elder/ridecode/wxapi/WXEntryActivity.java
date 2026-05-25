package com.elder.ridecode.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 微信SDK回调入口：必须通过 WXAPIFactory 创建的 IWXAPI 实例来处理 intent
        // 这里假设外部（如 MainActivity 或 Application）已将 IWXAPI 实例传入
        // 若未传入，则通过单例或静态变量获取
        IWXAPIWrapper.handleIntent(this, getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        IWXAPIWrapper.handleIntent(this, intent);
    }

    @Override
    public void onReq(BaseReq req) {
        // 微信请求回调，一般不需要处理
    }

    @Override
    public void onResp(BaseResp resp) {
        // 处理微信回调
        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchResp = (WXLaunchMiniProgram.Resp) resp;
            String extraMsg = launchResp.extMsg;

            int errCode = resp.errCode;
            switch (errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    Toast.makeText(this, "跳转成功", Toast.LENGTH_SHORT).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    Toast.makeText(this, "用户取消", Toast.LENGTH_SHORT).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    Toast.makeText(this, "授权被拒绝", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "返回错误: " + errCode, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        finish();
    }
}