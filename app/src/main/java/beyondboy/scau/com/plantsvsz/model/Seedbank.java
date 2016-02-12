package beyondboy.scau.com.plantsvsz.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.tencent.bugly.crashreport.BuglyLog;

import beyondboy.scau.com.plantsvsz.view.GameView;
import static beyondboy.scau.com.plantsvsz.util.Config.*;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-02-02
 * Time: 03:24
 * 处理面板上面的对象:向日葵\豌豆和等待安放到跑道的对象
 */
public class Seedbank extends BaseModel implements TouchAble
{
    // status==SEED_FLOWER表示当前对象就是向日葵
    public static final int SEED_FLOWER = 1;
    // status==SEED_PEA表示当前对象就是豌豆
    public static final int SEED_PEA = 2;
    // status==EMPLACE_FLOWER表示当前对象就是等待安放到跑道的向日葵
    public static final int EMPLACE_FLOWER = 10;
    // status==EMPLACE_PEA表示当前对象就是等待安放到跑道的豌豆
    public static final int EMPLACE_PEA = 11;
    private static final String TAG = Seedbank.class.getName();

    public Seedbank(int locationX, int locationY, int status,Bitmap bitmap)
    {
        super(locationX, locationY, status, bitmap);
       // BuglyLog.i(TAG, "location:  " + locationX + "  locationY:   " + locationY);
    }

    public Seedbank(int locationX, int locationY, int status)
    {
        this(locationX, locationY, status, null);
    }

    @Override
    public void drawSelf(Canvas canvas)
    {
        if (states == SEED_FLOWER||states==SEED_PEA)
        {
            // 面板上面向日葵
            canvas.drawBitmap(mBitmap, this.locationX, this.locationY, null);
        }
        else if (states == EMPLACE_FLOWER)
        {
            // 等待安放到跑道的向日葵
            canvas.drawBitmap(flowerBitmaps[0], this.locationX, this.locationY, null);
        }
        else if (states == EMPLACE_PEA)
        {
            // 安放到跑道的豌豆
            canvas.drawBitmap(peaBitmaps[0], this.locationX, this.locationY, null);
        } else
        {
            throw new RuntimeException("没有找到对应面板的对象:" + states);
        }
        //BuglyLog.i(TAG, "location:  " + this.locationX + "  locationY:   " + this.locationY);
    }

    @Override
    public boolean onTouch(MotionEvent event)
    {
        int x=(int)event.getX();
        int y=(int)event.getY();
        //检查是否选中了该图片
        if(mRect.contains(x,y))
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    BuglyLog.i(TAG,"选中了面板上面对象,把面板对象变成等待安放到跑道的对象");
                    if(states==SEED_FLOWER||states==SEED_PEA)
                    {
                        GameView.getInstanse().addEmplace(this);
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (states == EMPLACE_FLOWER || states == EMPLACE_PEA)
                    {
                        System.out.println("等待安放到跑道的对象在移动");
                        // locationX,locationY实际是图片的左上角的坐标
                        // 把图片的左上角的坐标做了修正,是触屏点坐标始终处于绘制图片的中心
                        this.locationX = x - flowerBitmaps[0].getWidth() / 2;
                        this.locationY = y - flowerBitmaps[0].getHeight() / 2;
                        // 利用矩形对象的方法:offsetTo
                        this.mRect.offsetTo(this.locationX, this.locationY);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (states == EMPLACE_FLOWER || states == EMPLACE_PEA)
                    {
                        // 在跑道里面寻找合适的位置进行安放
                        BuglyLog.i(TAG, "在跑道里面寻找合适的位置进行安放");
                        GameView.getInstanse().addPlan(this);
                    }
                    return true;
            }
        }
        else
        {
            BuglyLog.i(TAG, "没有选中");
        }
        return false;
    }
}
