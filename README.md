# ZRouter
android路由跳转库，支持完整的scheme如**scheme://home/main?id=123**,支持自定义拦截器，支持界面配置多个scheme

### 介绍
该项目使用编译时注解结合apt动态生成代码收集各个界面的数据包括参数，可以自己添加相应的拦截器来进行一些特殊scheme的处理，比如http链接可以直接跳转H5界面

### 使用
### 注解 
**path** - 界面的标识，一般由home/main两部分组成，模块名和界面名称，可前后端统一规范的scheme

**paramAlias** - 传递的参数名称

**paramName** - 界面解析的参数名
以上两个注解用于防止参数不一致的情况，需要转换一下

**paramType** - 参数类型

**needLogined** - 打开界面需要登录

**needClickable** - 防止重复打开界面

### 配置
在build的defaultConfig里面配置

```
javaCompileOptions {
    annotationProcessorOptions {
        arguments = [routerType :'Home']
    }
}
```
Home为模块名，用于apt生成类名的拼接，判别组件化的生成各个类的区别，导包

```
compile project(':router-compiler')
provided project(":annoation")
annotationProcessor project(':router')
``` 

在相应的activity上打上注解：

```
@Router(path = {"home/init","home/dd"},paramAlias = {"s","s1"},paramName = {"d1","d2"},
paramType = {"s","s"},needLogined = false,needClickable = true)
public class InitActivity extends AppCompatActivity {
```
#### 初始化
采用的是反射的机制进行初始化参数，指定初始化的模块和scheme,添加相应的拦截器

```
private static String [] mMouduleName = new String[] {
        "Home"
};

private static String [] mSchemes = new String[] {
        "scheme://"
};

public static void init() {
    SchemeRouter.setup(mMouduleName,mSchemes);
    SchemeRouter.addInterrupt(new HttpSchemeInterrupt());
    SchemeRouter.addInterrupt(new ErrorInterrupt());
    SchemeRouter.addInterrupt(new DebounceInterrupt());
    SchemeRouter.addInterrupt(new LoginAndCarInterrupt());
}
```
这块后续可以再优化，毕竟在android端不太建议用反射来初始化数据，可采用ASM,javassit插入代码进行初始化

#### 使用
采用Builder模式，可以添加参数

```
 RouterNavigation.navigation()
	    .navigate(MainActivity.this,"home/init")
	    .appendQueryParameter("s","ssss")
	    .appendQueryParameter("s1","ddd")
	    .addBundle(_bundle)
	    .start();
```
也可以跳转一个完整的scheme

```
RouterNavigation.navigation()
	    .navigate(MainActivity.this,"scheme://home/init?id=123")
	    .start();
```



