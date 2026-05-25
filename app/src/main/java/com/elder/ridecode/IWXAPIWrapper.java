package com.elder.ridecode;

import android.app.Activity;
import android.content.Intent;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * IWXAPI 实例的静态持有者，供 WXEntryActivity 回调使用。
 * 微信SDK要求 WXEntryActivity 必须在 wxapi 包下，但 IWXAPI 实例
 * 通常在 Application 或 MainActivity 中创建，通过此类桥接。
 */
public class IWXAPIWrapper {

    private static volatile IWXAPI sApi;

    public static void setApi(IWXAPI api) {
        sApi = api;
    }

    public static void clearApi() {
        sApi = null;
    }

    public static void handleIntent(Activity activity, Intent intent) {
        if (activity == null) {
            return;
        }
        if (sApi != null) {
            sApi.handleIntent(intent, (IWXAPIEventHandler) activity);
        }
        activity.finish();
    }
}