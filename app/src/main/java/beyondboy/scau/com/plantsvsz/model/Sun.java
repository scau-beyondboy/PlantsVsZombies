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
    // 阳光的状态
    // status==SHOW表示阳光等待收集
    public static final int SHOW = 1;
    // status==MOVE表示阳光已经收集
    public static final int MOVE = 2;
    private long createSunTime;
    // 收集阳光每次移动的距离
    private float moveX, moveY;
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
        if(this.states==SHOW)
        {
            // 一定的时间范围内没有收集阳光,阳光死亡
            if(System.currentTimeMillis()-createSunTime>Config.deadSun)
            {
                this.lifeValue=0;
            }
        }
        else if(this.states==MOVE)
        {
            this.locationX-=moveX;
            this.locationY-=moveY;
            // 已经到顶部了
            if(this.locationY<=0)
            {
                this.lifeValue=0;
                // 增加分值
                Config.totalScore+=Config.sunScore;
            }
        }
        else
        {
            throw new RuntimeException("没有找到阳光的对象:" + this.states);
        }
    }

    @Override
    public boolean onTouch(MotionEvent event)
    {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // 只考虑触屏按下的情况
        if(event.getAction()==MotionEvent.ACTION_DOWN&&this.mRect.contains(x,y))
        {
            this.states=MOVE;
            // 计算阳光到收集面板的距离
            int sun2Seedx=this.locationX-Config.seedbankX;
            int sun2Seedy=this.locationY-0;
            // 计算每次移动的距离
            moveX=sun2Seedx>>Config.moveSun;
            moveY=sun2Seedy>>Config.moveSun;
        }
        return false;
    }
}
