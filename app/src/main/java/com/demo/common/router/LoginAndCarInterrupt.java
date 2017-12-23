package com.demo.common.router;

import android.content.Context;

import com.module.common.router.compiler.Interrupt;
import com.module.common.router.compiler.SchemeBean;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-9-22
 */

public class LoginAndCarInterrupt implements Interrupt {

    @Override
    public boolean interrupt(Context context, String scheme, SchemeBean schemeBean) {
        return false;
    }
}
