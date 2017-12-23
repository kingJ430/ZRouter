package com.demo.common.router;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.module.common.annoation.Router;
import com.module.common.router.compiler.RouterNavigation;
import com.module.common.router.compiler.SchemeRouter;


@Router(path = {"home/init","home/dd"},paramAlias = {"s","s1"}
,paramName = {"d1","d2"},paramType = {"s","s"},needLogined = false,needClickable = true)
public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        String param1 = getIntent().getStringExtra("d1");
        String param2 = getIntent().getStringExtra("d2");
        Bundle _bundle = getIntent().getBundleExtra(SchemeRouter.KEY_BUNDLE);
        Log.e("InitActivity","param1 = " + param1
         + "  param2 = " + param2 + " ---" + _bundle.getString("MainActivity"));

        findViewById(R.id.init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SchemeRouter.open(InitActivity.this,
//                        "/home/main");
                RouterNavigation.navigation()
                        .navigate(InitActivity.this,"home/main")
                        .start();
            }
        });
    }
}
