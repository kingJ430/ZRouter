package com.module.common.router.utils;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-8-31a
 */
public class CreateRouterMappingUtils {

    public static void createMapping(String moduleName
            , List<Map> _mapList, Filer filer) {
        ClassName scheme = ClassName.get("com.module.common.router.compiler", "SchemeBean");
        ClassName router = ClassName.get("com.module.common.router.compiler", "SchemeRouter");


        MethodSpec.Builder mapBuilder = MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class);
        mapBuilder.addStatement("$T scheme = null",scheme);
        mapBuilder.addStatement("$T pathModuleScheme = null",scheme);

        for(Map<String,Object> _stringObjectMap : _mapList) {
            mapBuilder.addCode("\n");
            mapBuilder.addStatement("scheme = new $T()",scheme);
            String[] path = null;
            for (Map.Entry<String, Object> _stringStringEntry : _stringObjectMap.entrySet()) {
                if("path".equals(_stringStringEntry.getKey())) {
                    path = (String[]) _stringStringEntry.getValue();
                    mapBuilder.addStatement("scheme.setPath($S)",path[0]);
                }
                if("className".equals(_stringStringEntry.getKey())) {
                    mapBuilder.addStatement("scheme.setActivityclass($T.class)",_stringStringEntry.getValue());
                }
                if("paramAlias".equals(_stringStringEntry.getKey())) {
                    String[] paramAlias = (String[]) _stringStringEntry.getValue();
                    if(paramAlias != null && paramAlias.length > 0) {
                        mapBuilder.addStatement("scheme.setParamAlias(new String["+ paramAlias.length +"])");
                        for(int i = 0;i < paramAlias.length;i ++) {
                            mapBuilder.addStatement("scheme.getParamAlias()["+ i +"] = $S", paramAlias[i]);
                        }
                    }

                }

                if("paramName".equals(_stringStringEntry.getKey())) {
                    String[] paramName = (String[]) _stringStringEntry.getValue();
                    if(paramName != null && paramName.length > 0) {
                        mapBuilder.addStatement("scheme.setParamName(new String["+ paramName.length +"])");
                        for(int i = 0;i < paramName.length;i ++) {
                            mapBuilder.addStatement("scheme.getParamName()["+ i +"] = $S", paramName[i]);
                        }
                    }

                }

                if("paramType".equals(_stringStringEntry.getKey())) {
                    String[] paramType = (String[]) _stringStringEntry.getValue();
                    if(paramType != null && paramType.length > 0) {
                        mapBuilder.addStatement("scheme.setParamType(new String["+ paramType.length +"])");
                        for(int i = 0;i < paramType.length;i ++) {
                            mapBuilder.addStatement("scheme.getParamType()["+ i +"] = $S", paramType[i]);
                        }
                    }

                }

                if("needLogined".equals(_stringStringEntry.getKey())) {
                    mapBuilder.addStatement("scheme.setNeedLogined("+_stringStringEntry.getValue()+")");
                }

                if("needClickable".equals(_stringStringEntry.getKey())) {
                    mapBuilder.addStatement("scheme.setNeedClickable("+_stringStringEntry.getValue()+")");
                }
            }
            mapBuilder.addStatement("$T.mapScheme(scheme)",router);
            mapBuilder.addCode("\n");
            if(path.length > 1) {
                for(int j = 1;j < path.length;j ++) {
                    mapBuilder.addStatement("pathModuleScheme = new $T()",scheme);
                    mapBuilder.addStatement("pathModuleScheme.setPath($S)",path[j]);
                    mapBuilder.addStatement("pathModuleScheme.setActivityclass(scheme.getActivityclass())");
                    mapBuilder.addStatement("pathModuleScheme.setParamAlias(scheme.getParamAlias())");
                    mapBuilder.addStatement("pathModuleScheme.setParamName(scheme.getParamName())");
                    mapBuilder.addStatement("pathModuleScheme.setParamType(scheme.getParamType())");
                    mapBuilder.addStatement("pathModuleScheme.setNeedLogined(scheme.isNeedLogined())");
                    mapBuilder.addStatement("pathModuleScheme.setNeedCar(scheme.isNeedCar())");
                    mapBuilder.addStatement("pathModuleScheme.setNeedClickable(scheme.isNeedClickable())");
                    mapBuilder.addStatement("$T.mapScheme(pathModuleScheme)",router);
                    mapBuilder.addCode("\n");

                }
            }
            mapBuilder.addCode("\n");
        }


        MethodSpec map = mapBuilder
                .build();

//        FieldSpec schemeList = FieldSpec.builder(listOfScheme, "mSchemeList")
//                .addModifiers(Modifier.PRIVATE,Modifier.STATIC)
//                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("RouterMap" + moduleName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                .addField(schemeList)
                .addMethod(map)
                .build();

        try {
            JavaFile.builder("com.module.common.router", typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
