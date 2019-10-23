package org.android.study.materialedittext;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author Administrator
 * @name [Utils]
 * @time 2019/10/23 9:15
 * @describe
 */
public class Utils {

    public static float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
