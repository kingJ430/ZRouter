package com.module.common.router.compiler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-9-2
 */
public class SchemeRouter {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";

    public static Map<String,SchemeBean> mSchemeMap = new HashMap<>();
    public static List<Interrupt> mInterruptList = new ArrayList<>();
    public static final String PATH = "routerpath"; //用于获取跳到当前页面的path
    public static String[] moduleName = null;
    public static String[] schemeHost = null;

    public synchronized static void setup(String[] bundlesName,String[] pSchemeHost) {
        moduleName = bundlesName;
        schemeHost = pSchemeHost;
        initMappings();
    }

    public static void addInterrupt(Interrupt pInterrupt) {
        mInterruptList.add(pInterrupt);
    }

    public static void removeInterrupt(Interrupt pInterrupt) {
        mInterruptList.remove(pInterrupt);
    }

    public static void clearInterrupts() {
        mInterruptList.clear();
    }

    /**
     * 初始化路由表 target->activity
     */
    private static void initMappings() {
        if (!mSchemeMap.isEmpty() || moduleName == null) {
            return;
        }
        for (String bundleName : moduleName) {
            try {
                Class<?> c1 = Class.forName("com.module.common.router.RouterMap" + bundleName);
                c1.getMethod("map").invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void mapScheme(SchemeBean schemeBean) {
//        for (Map.Entry<String, SchemeBean> _schemeEntry : mSchemeMap.entrySet()) {
//            if(schemeBean.getPath() == null || schemeBean.getPath().equals(_schemeEntry.getKey())) {
//                return;
//            }
//        }
        mSchemeMap.put(schemeBean.getPath(),schemeBean);
    }

    static void open(Activity pActivity,String scheme) {
        open(pActivity,scheme,-1,null);
    }

    static void open(Context pContext,String scheme) {
        open((Activity) pContext,scheme);
    }

    public static void open(Context pContext,String scheme,Bundle pBundle) {
        open((Activity) pContext,scheme,-1,pBundle);
    }


    static void open(Activity pActivity,String scheme,int requestCode) {
        open(pActivity,scheme,requestCode,null);

    }

    static void open(Activity pActivity, String scheme, int requestCode, Bundle pBundle) {
        Intent intent = getJumpNextActivityIntent(scheme,pActivity);
        if(intent != null ) {
            if(pBundle != null) {
                intent.putExtra(KEY_BUNDLE, pBundle);
            }
            pActivity.startActivityForResult(intent,requestCode);
        }

    }

    static void open(Fragment pFragment, String scheme) {
        open(pFragment,scheme,-1,null);
    }

    static void open(Fragment pFragment, String scheme,int requestCode) {
        open(pFragment,scheme,requestCode,null);
    }


    /**
     * 判断条件打开界面
     * @param pFragment
     * @param scheme
     * @param requestCode
     */
    static void open(Fragment pFragment, String scheme, int requestCode,Bundle pBundle) {
        Intent intent = getJumpNextActivityIntent(scheme,pFragment.getActivity());
        if(intent != null ) {
            if(pBundle != null) {
                intent.putExtra(KEY_BUNDLE, pBundle);
            }
            pFragment.startActivityForResult(intent,requestCode);
        }

    }

    private static Intent getJumpNextActivityIntent(String scheme, Context pContext) {

        if(mSchemeMap.isEmpty()) {
            setup(moduleName,schemeHost);
        }
        Intent intent = null;
        SchemeBean schemeBean = RouterMappingUtils.getScheme(scheme);
        for(Interrupt interrupt : mInterruptList) {
            if(interrupt.interrupt(pContext,scheme,schemeBean)) {
                return null;
            }
        }
        intent = RouterMappingUtils.parseExtras((Activity) pContext, scheme);
        return intent;
    }




    public static boolean doOpen(String  scheme) {
        if(TextUtils.isEmpty(scheme)) {
            return false;
        }
        String path = RouterMappingUtils.getPath(scheme);
        if(TextUtils.isEmpty(path)) {
            return false;
        }
        for (Map.Entry<String, SchemeBean> _schemeEntry : SchemeRouter.mSchemeMap.entrySet()) {
            if (path.equals(_schemeEntry.getKey())) {
                return true;
            }
        }
        return false;
    }



}
