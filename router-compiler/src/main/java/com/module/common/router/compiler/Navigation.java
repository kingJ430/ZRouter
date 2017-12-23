package com.module.common.router.compiler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.Map;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-9-10
 */
public interface Navigation {

    Navigation appendQueryParameter(String key, String value);
    Navigation appendQueryMap(Map<String,String> pMap);
    Navigation appendQueryInteger(String key, int value);
    Navigation appendQueryBoolean(String key, boolean value);
    Navigation appendQueryLong(String key, long value);
    Navigation navigate(Context context, String baseUrl);
    Navigation navigate(Activity activity, String baseUrl, int request);
    Navigation navigate(Fragment fragment, String baseUrl,int request);
    Navigation navigateWithContext(Context context, String baseUrl, int request);
    Navigation addSerializable(String key, Serializable object);
    Navigation addParcelable(String key, Parcelable object);
    Navigation addBundle(Bundle pBundle);
    void start();


}
