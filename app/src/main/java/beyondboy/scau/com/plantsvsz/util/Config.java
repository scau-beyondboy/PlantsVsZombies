package beyondboy.scau.com.plantsvsz.util;

import android.graphics.Bitmap;
import android.graphics.Point;

import beyondboy.scau.com.plantsvsz.MyApplication;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-01-30
 * Time: 14:34
 */
public final class Config
{
    public static final MyApplication APPCONTEXT=MyApplication.getInstance();
    /**屏幕宽度和高度*/
    public static final Point SCREENINFO=DisplayUtil.displayConfig();
    /**获取独立像素*/
    public static final int DENSITYDPI=DisplayUtil.getDensityDpi();
    // 背景图片
    public static Bitmap bkBitmap;
}
