package com.module.common.router.compiler;

import android.content.Context;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-9-22
 */

public interface Interrupt {

    boolean interrupt(Context context,String scheme, SchemeBean pSchemeBean);

}
