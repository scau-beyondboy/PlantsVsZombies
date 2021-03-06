package beyondboy.scau.com.plantsvsz.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.SparseArray;

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
    // 跑道里面单元格宽高
    public static int cellWidth;
    public static int cellHeight;
    // 跑道的集合
    public static SparseArray<Point> plantPoints;
    // 定义阳光的图片
    public static Bitmap sunBitmap;
    // 以下属于游戏的业务时间，此时间的设置会增加游戏的趣味性，难度:游戏策划人员设定的
    // 跑道里面的向日葵出现4秒钟之后产生阳光
    public static int createSunTime = 4000;
    // 跑道里面的豌豆出现4秒钟之后产生子弹
    public static int createBulletTime = 4000;
    // 一定的时间范围内没有收集阳光,阳光死亡
    public static int deadSun = 3000;
    // 收集阳光的速度
    public static int moveSun = 4;
    // 游戏的初始化分值
    public static int totalScore = 300;
    // 阳光收集一次分值
    public static int sunScore = 25;
    // 添加一次向日葵一次分值
    public static int flowerScore = 50;
    // 添加一次豌豆分值
    public static int peaScore = 100;
    // 子弹移动的x
    public static int moveBulletX = 8;
    // 僵尸移动的x
    public static int moveZombieX = 5;
    // 定义子弹的图片
    public static Bitmap bulletBitmap;
    // 定义僵尸的图片
    public static Bitmap[] zombieBitmaps = new Bitmap[7];
    // 一定的时间范围内产生子弹
    public static int createBullet=4000;
    // 定义5个跑道索引对应的y坐标(就是僵尸的y坐标):在初始化plantPoints
    public static int[] raceWayYpoints = new int[5];
    // 一定的时间范围内产生僵尸
    public static int createZombie = 3000;
}
