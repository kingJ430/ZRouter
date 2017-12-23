package com.module.common.router;

import com.google.auto.service.AutoService;
import com.module.common.annoation.Router;
import com.module.common.router.utils.CreateRouterMappingUtils;
import com.module.common.router.utils.RouterAnnoationClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Messager messager;
    private String ModuleName;
    private Filer mFiler;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
        typeUtils = processingEnv.getTypeUtils();
        Map<String, String> processingEnvOptions = processingEnv.getOptions();
        for (Map.Entry<String, String> _stringStringEntry : processingEnvOptions.entrySet()) {
            System.out.println(_stringStringEntry.getKey() + ":" + _stringStringEntry.getValue());
            if ("routerType".equals(_stringStringEntry.getKey())) {
                ModuleName = _stringStringEntry.getValue();
                break;
            }
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> activitieElements = roundEnv.getElementsAnnotatedWith(Router.class);
        List<Map> _mapList = new ArrayList<>();
        for (Element _element : activitieElements) {
            // Check if a class has been annotated with @Factory
            if (_element.getKind() != ElementKind.CLASS) {
                error("Router can only apply on class");
                return true;
            }
            if(!isValidClass((TypeElement) _element)) {
                error("The class " + ((TypeElement) _element).getQualifiedName() + " is not an Activity");
                return true;
            }

            TypeElement _typeElement = (TypeElement) _element;
            RouterAnnoationClass _RouterAnnoationClass
                    = new RouterAnnoationClass(_typeElement);
            Map<String, Object> mSchemeMap = _RouterAnnoationClass.getSchemeInfo();

            _mapList.add(mSchemeMap);
//            messager.printMessage(Diagnostic.Kind.NOTE, "数组: " + _mapList.size());

        }
        if (_mapList.size() > 0) {
            CreateRouterMappingUtils.createMapping(ModuleName, _mapList, mFiler);
        }

        return false;
    }

    private void error(String error) {
        messager.printMessage(Diagnostic.Kind.ERROR, error);
    }

    /**
     * Verify the annotated class is Activity.
     *
     * @param typeElement TypeElement
     * @return True if legal, false otherwise.
     */
    private boolean isValidClass(TypeElement typeElement)  {
        Set<Modifier> modifiers = typeElement.getModifiers();
        // not an activity error.
        if (!veritySuperClass(typeElement, "android.app.Activity")) {
            return false;
        }
        return true;
    }

    /**
     * Verify an Activity
     *
     * @param type       TypeElement
     * @param superClass Super class name.
     * @return True if verified, false otherwise.
     */
    private boolean veritySuperClass(TypeElement type, String superClass) {
        return !(type == null || "java.lang.Object".equals(type.getQualifiedName().toString()))
                && (type.getQualifiedName().toString().equals(superClass)
                || veritySuperClass((TypeElement) typeUtils.asElement(
                type.getSuperclass()), superClass));
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Router.class.getCanonicalName());
    }
}
