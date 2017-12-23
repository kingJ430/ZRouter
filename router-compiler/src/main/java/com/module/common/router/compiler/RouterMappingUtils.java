package com.module.common.router.compiler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Map;

public class RouterMappingUtils {

    public static String getPath(String scheme) {
        if(TextUtils.isEmpty(scheme)) {
            return null;
        }
        boolean isScheme = false;
        if(SchemeRouter.schemeHost != null) {
            for(int i = 0; i < SchemeRouter.schemeHost.length; i ++) {
                if(scheme.startsWith(SchemeRouter.schemeHost[i])) {
                    isScheme = true;
                    break;
                }
            }
        } else {
            if(scheme.startsWith("router://")) {
                isScheme = true;
            }
        }
        if(!isScheme) {
            return null;
        }
        Uri uri = Uri.parse(scheme);
        return uri.getHost() + uri.getPath();
    }

    public static SchemeBean getScheme(String scheme) {
        String path = getPath(scheme);
        if(TextUtils.isEmpty(path)) {
            return null;
        }
        for (Map.Entry<String, SchemeBean> _schemeEntry : SchemeRouter.mSchemeMap.entrySet()) {
            if (path.equals(_schemeEntry.getKey())) {
                return _schemeEntry.getValue();
            }
        }
        return null;
    }

    public static Intent parseExtras(Context context,String scheme) {
        String path = getPath(scheme);
        if(TextUtils.isEmpty(path)) {
            return null;
        }
        Uri uri = Uri.parse(scheme);
        for (Map.Entry<String, SchemeBean> _schemeEntry : SchemeRouter.mSchemeMap.entrySet()) {
            if(path.equals(_schemeEntry.getKey())) {
                SchemeBean schemeBean = _schemeEntry.getValue();
                Intent intent = new Intent();
                intent.setClass(context,schemeBean.getActivityclass());
                intent.putExtra(SchemeRouter.PATH,schemeBean.getPath());
                if(schemeBean.getParamAlias() != null) {
                    for (int i = 0; i < schemeBean.getParamAlias().length; i++) {
                        String param = uri.getQueryParameter(schemeBean.getParamAlias()[i]);
                        if(param == null) {
                            continue;
                        }
                        try {
                            if (RouterConstant.KEY_INTEGER.equalsIgnoreCase(schemeBean.getParamType()[i])) {
                                intent.putExtra(schemeBean.getParamName()[i], Integer.valueOf(param));
                            } else if (RouterConstant.KEY_BOOLEAN.equalsIgnoreCase(schemeBean.getParamType()[i])) {
                                intent.putExtra(schemeBean.getParamName()[i], Boolean.valueOf(param));
                            } else if (RouterConstant.KEY_LONG.equalsIgnoreCase(schemeBean.getParamType()[i])) {
                                intent.putExtra(schemeBean.getParamName()[i], Long.valueOf(param));
                            } else if (RouterConstant.KEY_FLOAT.equalsIgnoreCase(schemeBean.getParamType()[i])) {
                                intent.putExtra(schemeBean.getParamName()[i], Float.valueOf(param));
                            } else if (RouterConstant.KEY_DOUBLE.equalsIgnoreCase(schemeBean.getParamType()[i])) {
                                intent.putExtra(schemeBean.getParamName()[i], Double.valueOf(param));
                            } else {
                                intent.putExtra(schemeBean.getParamName()[i], param);
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
                return intent;
            }
        }
        return null;
    }

    /**
     * 跳转透明界面
     * @param context
     * @param scheme
     * @return
     */
    public static Intent parseSchemeExtras(Context context,String scheme) {
        Uri uri = Uri.parse(scheme);
        Intent intent = new Intent();
        intent.setData(uri);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }



    public static String getSchemePath(String scheme) {
        if(scheme == null) return null;
        Uri uri = Uri.parse(scheme);
        String path = uri.getPath();
        return path;
    }

    public static String getHost(String scheme) {
        if(scheme == null) return null;
        Uri uri = Uri.parse(scheme);
        String host = uri.getScheme();
        return host;
    }

    public static String  getAction(String scheme) {
        if(scheme == null) return null;
        Uri uri = Uri.parse(scheme);
        String host = uri.getLastPathSegment();
        return host;
    }

    public static String getTarget(String scheme) {
        if(scheme == null) return null;
        Uri uri = Uri.parse(scheme);
        String host = uri.getHost();
        return host;
    }

}
