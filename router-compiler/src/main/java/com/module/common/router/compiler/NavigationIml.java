package com.module.common.router.compiler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-9-10
 */
public class NavigationIml implements Navigation {

    private Context mContext;
    private Activity mActivity;
    private Fragment mFragment;
    private String mBaseUrl;
    private int mRequestCode = -1;
    private Map<String,String> mParameters = new HashMap<>();
    private Intent mIntent;

    private Bundle mBundle;

    @Override
    public Navigation appendQueryParameter(String key, String value) {
        mParameters.put(key,value);
        return this;
    }

    @Override
    public Navigation appendQueryMap(Map<String,String> pMap) {
        if(pMap != null && pMap.size() > 0) {
            mParameters.putAll(pMap);
        }
        return this;
    }

    @Override
    public Navigation appendQueryInteger(String key, int value) {
        mParameters.put(key,value+"");
        return this;
    }

    @Override
    public Navigation appendQueryBoolean(String key, boolean value) {
        mParameters.put(key,value+"");
        return this;
    }

    @Override
    public Navigation appendQueryLong(String key, long value) {
        mParameters.put(key,value+"");
        return this;
    }

    @Override
    public Navigation navigate(Context context, String baseUrl) {
        return navigateWithContext(context,baseUrl,mRequestCode);
    }


    @Override
    public Navigation navigate(Activity pActivity, String baseUrl,int pRequestCode) {
        mActivity = pActivity;
        return navigateWithContext(pActivity,baseUrl,pRequestCode);
    }


    @Override
    public Navigation navigate(Fragment pFragment, String baseUrl,int pRequestCode) {
        mFragment = pFragment;
        return navigateWithContext(pFragment.getActivity(),baseUrl,pRequestCode);
    }

    @Override
    public Navigation navigateWithContext(Context context, String baseUrl, int pRequestCode) {
        mContext = context;
        mBaseUrl = baseUrl;
        mRequestCode = pRequestCode;
        return this;
    }

    @Override
    public Navigation addSerializable(String key, Serializable object) {
        if(mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putSerializable(key,object);
        return this;
    }

    @Override
    public Navigation addParcelable(String key, Parcelable object) {
        if(mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putParcelable(key,object);
        return this;
    }

    @Override
    public Navigation addBundle(Bundle pBundle) {
        if(mBundle == null) {
            mBundle = pBundle;
        } else {
            mBundle.putAll(pBundle);
        }
        return this;
    }


    @Override
    public void start() {
        String scheme = getUri();
        Log.e("router",scheme);
//        mIntent = RouterMappingUtils.parseSchemeExtras(mContext,scheme);
        if(mActivity != null) {
            SchemeRouter.open(mActivity,scheme,mRequestCode,mBundle);
        } else if(mFragment != null) {
            SchemeRouter.open(mFragment,scheme,mRequestCode,mBundle);
        } else {
            SchemeRouter.open(mContext,scheme,mBundle);
        }

    }

    private String getUri() {
        StringBuffer url = new StringBuffer();
        if(!mBaseUrl.startsWith("http") && !mBaseUrl.startsWith("https")
                && !mBaseUrl.contains("://")) {
            if(SchemeRouter.schemeHost != null && SchemeRouter.schemeHost.length > 0) {
                url.append(SchemeRouter.schemeHost[0]);
            } else {
                url.append("router://");
            }
        }
        url.append(mBaseUrl);
        if(!mParameters.isEmpty()) {
            url.append("?");
            Set<Map.Entry<String ,String>> paramerSet = mParameters.entrySet();
            for(Map.Entry<String,String> paramer : paramerSet) {
                String value = paramer.getValue();
                if(!TextUtils.isEmpty(value)) {
                    String decodeValue = Uri.decode(value);
                    value = Uri.encode(decodeValue);
                }
                url.append(paramer.getKey() + "=" + value);
                url.append("&");
            }
        }
        return url.toString();
    }
}
