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
    /**缩放比例*/
    public static float scaleWidth;
    public static float scaleHeight;
    // 背景图片
    public static Bitmap bkBitmap;
    public static int seedbankX;// 面板图片左上角的x坐标
    public static Bitmap seedbankBitmap;
    public static Bitmap seedFlowerBitmap;
    public static Bitmap seedPeaBitmap;
    // 定义跑道的图片
    public static Bitmap[] flowerBitmaps = new Bitmap[8];
    public static Bitmap[] peaBitmaps = new Bitmap[8];
}
