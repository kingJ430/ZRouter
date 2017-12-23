package com.demo.common.router;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.module.common.annoation.Router;
import com.module.common.router.compiler.RouterNavigation;


@Router(path = {"home/main"},paramAlias = {"id","s"})
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SchemeRouter.open(MainActivity.this,"/home/init");
                Bundle _bundle = new Bundle();
                _bundle.putString("MainActivity","android");
                RouterNavigation.navigation()
                        .navigate(MainActivity.this,"home/init")
                        .appendQueryParameter("s","ssss")
                        .appendQueryParameter("s1","ddd")
                        .addBundle(_bundle)
                        .start();
            }
        });
    }
}
