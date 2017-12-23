package com.demo.common.router;

import com.module.common.router.compiler.SchemeRouter;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-9-23
 */

public class SchemeManager {

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
}
