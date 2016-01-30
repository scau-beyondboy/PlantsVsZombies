package beyondboy.scau.com.plantsvsz.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-01-30
 * Time: 14:04
 * 获取屏幕信息和转换像素
 */
public final class DisplayUtil
{
    /**获取手机屏幕宽高*/
    public static Point displayConfig()
    {
        Point displayConfig=new Point();
        WindowManager wm = (WindowManager)Config.APPCONTEXT.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(displayConfig);
        return displayConfig;
    }
    /**获取像素密度*/
    public static int getDensityDpi()
    {
        DisplayMetrics displayMetrics =Config.APPCONTEXT.getResources().getDisplayMetrics();
        return displayMetrics.densityDpi;
    }
}
