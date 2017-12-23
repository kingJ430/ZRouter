package com.module.common.router.utils;

import com.module.common.annoation.Router;
import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;

/**
 * 根据注解收集信息
 * @author liuxia
 * @version 3.7
 * @date 16-8-31
 */
public class RouterAnnoationClass {

    private TypeElement mAnnotatedClassElement;
    private Map<String ,Object> mSchemeMap = new HashMap<>();

    public RouterAnnoationClass(TypeElement pAnnotatedClassElement) {
        mAnnotatedClassElement = pAnnotatedClassElement;
    }

    public Map<String ,Object> getSchemeInfo() {
        Router router = mAnnotatedClassElement.getAnnotation(Router.class);
//        String target = router.target();
//        String action = router.action();
        String[] path = router.path();
//        ClassName className = ClassName.get(mAnnotatedClassElement);
        ClassName className = ClassName.get((TypeElement) mAnnotatedClassElement);
//        String className = mAnnotatedClassElement.getQualifiedName();
        String[] paramAlias = router.paramAlias();
        String[] paramName = router.paramName();
        String[] paramType = router.paramType();
        boolean needLogined = router.needLogined();
        boolean needClickable = router.needClickable();
        Map<String ,Object> mSchemeMap = new HashMap<>();
        mSchemeMap.put("path",path);

        mSchemeMap.put("className",className);
//        mSchemeMap.put("action",action);
        mSchemeMap.put("paramAlias",paramAlias);
        mSchemeMap.put("paramName",paramName);
        mSchemeMap.put("paramType",paramType);
        mSchemeMap.put("needLogined",needLogined);

        mSchemeMap.put("needClickable",needClickable);
        return mSchemeMap;

    }


}
