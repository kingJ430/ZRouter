package com.module.common.router.compiler;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-8-20
 */
public class SchemeBean {


    /**
     * name : violation
     * schemeList : [{"alias":"violation","className":"ViolationMainListActivity","paramAlias":"carId","paramName":"carID","paramType":"s","needLogined":true,"needCar":true}]
     */

    private String path;
    private Class Activityclass;
    private String[] paramAlias;
    private String[] paramName;
    private String[] paramType;
    private boolean needLogined;
    private boolean needCar;

    private boolean needClickable;

    public String getPath() {
        return path;
    }

    public void setPath(String pPath) {
        path = pPath;
    }

    public Class getActivityclass() {
        return Activityclass;
    }

    public void setActivityclass(Class pActivityclass) {
        Activityclass = pActivityclass;
    }

    public String[] getParamAlias() {
        return paramAlias;
    }

    public void setParamAlias(String[] paramAlias) {
        this.paramAlias = paramAlias;
    }

    public String[] getParamName() {
        return paramName;
    }

    public void setParamName(String[] paramName) {
        this.paramName = paramName;
    }

    public String[] getParamType() {
        return paramType;
    }

    public void setParamType(String[] paramType) {
        this.paramType = paramType;
    }

    public boolean isNeedLogined() {
        return needLogined;
    }

    public void setNeedLogined(boolean needLogined) {
        this.needLogined = needLogined;
    }

    public boolean isNeedCar() {
        return needCar;
    }

    public void setNeedCar(boolean needCar) {
        this.needCar = needCar;
    }

    public boolean isNeedClickable() {
        return needClickable;
    }

    public void setNeedClickable(boolean pNeedClickable) {
        needClickable = pNeedClickable;
    }

//    @Override
//    public String toString() {
//        return "path = " + path + " | class = " + Activityclass.getSimpleName();
//    }
}
