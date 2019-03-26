package com.demo.common.router;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.module.common.annoation.Router;
import com.module.common.router.compiler.RouterNavigation;
import com.module.common.router.compiler.SchemeRouter;


@Router(path = {"home/init","home/dd"},needLogined = false,needClickable = true)
public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Bundle _bundle = getIntent().getBundleExtra(SchemeRouter.KEY_BUNDLE);
        String param1 = _bundle.getString("s");
        String param2 = _bundle.getString("s1");
        int param3 = _bundle.getInt("h2",0);

        Log.e("InitActivity","param1 = " + param1
         + "  param2 = " + param2 + " ---" + _bundle.getString("MainActivity") + " param3= " + param3);

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
