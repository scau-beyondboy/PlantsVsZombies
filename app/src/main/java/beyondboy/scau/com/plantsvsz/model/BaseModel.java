package beyondboy.scau.com.plantsvsz.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import beyondboy.scau.com.plantsvsz.util.Config;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-02-02
 * Time: 03:16
 * 所有业务对象的父类
 */
public abstract class BaseModel
{
    //业务对象的x，y坐标
    protected int locationX;
    protected int locationY;
    //业务对象的生命值
    protected int life;
    // 矩形对象:判断是否触屏到了当前的对象
    protected Rect mRect;
    // 状态:区分是什么植物向日葵,豌豆,子弹
    protected int states;
    protected Bitmap mBitmap;

    public BaseModel(int locationX, int locationY, int status,Bitmap bitmap)
    {
        mBitmap=bitmap;
        this.locationX=locationX;
        this.locationY=locationY;
        this.states=status;
        if(mBitmap!=null)
        {
            // 实例化矩形对象:面板上面的图片的宽高是一致的
            mRect = new Rect(this.locationX, this.locationY, this.locationX + bitmap.getWidth(), this.locationY + bitmap.getHeight());
        }
        else
        {
            // 实例化矩形对象:面板上面的图片的宽高是一致的
            mRect = new Rect(this.locationX, this.locationY, this.locationX + Config.seedFlowerBitmap.getWidth(), this.locationY +Config.seedFlowerBitmap.getHeight());
        }
    }

    public int getLocationX()
    {
        return locationX;
    }

    public void setLocationX(int locationX)
    {
        this.locationX = locationX;
    }

    public int getLocationY()
    {
        return locationY;
    }

    public void setLocationY(int locationY)
    {
        this.locationY = locationY;
    }

    public int getLife()
    {
        return life;
    }

    public void setLife(int life)
    {
        this.life = life;
    }

    public Rect getRect()
    {
        return mRect;
    }

    public void setRect(Rect rect)
    {
        mRect = rect;
    }

    public int getStates()
    {
        return states;
    }
    // 绘制自己的方法
    public abstract void drawSelf(Canvas canvas);
    public void setStates(int states)
    {
        this.states = states;
    }
}
