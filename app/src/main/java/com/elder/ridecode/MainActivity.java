package com.elder.ridecode;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MainActivity extends AppCompatActivity {

    // TODO: 替换为微信开放平台注册的 AppID
    private static final String WX_APP_ID = "wx_your_app_id_here";

    private IWXAPI wxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化微信SDK
        wxApi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        wxApi.registerApp(WX_APP_ID);

        // 将 IWXAPI 实例传给 WXEntryActivity 的回调封装
        IWXAPIWrapper.setApi(wxApi);

        Button btnWechat = findViewById(R.id.btn_wechat);
        Button btnAlipay = findViewById(R.id.btn_alipay);

        // 微信乘车码按钮
        btnWechat.setOnClickListener(v -> openWechatRideCode());

        // 支付宝乘车码按钮
        btnAlipay.setOnClickListener(v -> openAlipayRideCode());
    }

    /**
     * 打开微信乘车码
     * 使用微信SDK WXLaunchMiniProgram.Req 跳转乘车码小程序
     */
    private void openWechatRideCode() {
        // 检测微信是否安装
        if (!wxApi.isWXAppInstalled()) {
            Toast.makeText(this, "未安装微信，请先安装微信", Toast.LENGTH_SHORT).show();
            try {
                Intent storeIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.tencent.mm"));
                storeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(storeIntent);
            } catch (ActivityNotFoundException ignored) {
            }
            return;
        }

        // 使用微信SDK跳转乘车码小程序
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_3cf62f4f1d52";   // 乘车码小程序原始ID
        req.path = "pages/index/index";      // 小程序首页路径
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE; // 正式版

        try {
            wxApi.sendReq(req);
        } catch (Exception e) {
            Toast.makeText(this, "微信SDK调用失败，尝试备用方式打开", Toast.LENGTH_SHORT).show();
            // 降级到 Intent 方式
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("weixin://app/wx5aa333606550dfd5/jumpWxa/?userName=gh_3cf62f4f1d52"));
                startActivity(intent);
            } catch (ActivityNotFoundException ignored) {
                Toast.makeText(this, "无法打开微信乘车码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 打开支付宝乘车码
     * 正确scheme: alipayqr://platformapi/startapp?saId=200011235
     */
    private void openAlipayRideCode() {
        try {
            // 支付宝乘车码正确scheme
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=200011235");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // 备用方案：直接打开支付宝
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.eg.android.AlipayGphone");
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(this, "请点击「付钱」→「乘车码」", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "未安装支付宝，请先安装支付宝", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
