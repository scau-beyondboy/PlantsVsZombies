package beyondboy.scau.com.plantsvsz.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import beyondboy.scau.com.plantsvsz.util.Config;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-02-14
 * Time: 20:50
 * 阳光:在一定的时间范围会有跑道里面的向日葵产生,一定时间会自动死亡,收集成功增加分值
 */
public class Sun extends BaseModel implements  TouchAble
{
    // status==SHOW表示阳光等待收集
    public static final int SHOW = 1;
    private long createSunTime;
    public Sun(int locationX, int locationY, int status, Bitmap bitmap)
    {
        super(locationX, locationY, status, bitmap);
        createSunTime=System.currentTimeMillis();
    }

    public Sun(int locationX, int locationY, int status)
    {
        this(locationX, locationY, status, Config.sunBitmap);
    }
    @Override
    public void drawSelf(Canvas canvas)
    {
        canvas.drawBitmap(Config.sunBitmap,this.locationX,this.locationY,null);
        // 一定的时间范围内没有收集阳光,阳光死亡
        if(System.currentTimeMillis()-createSunTime>Config.deadSun)
        {
            this.lifeValue=0;
        }
    }

    @Override
    public boolean onTouch(MotionEvent event)
    {
        return false;
    }
}
