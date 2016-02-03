package beyondboy.scau.com.plantsvsz.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.tencent.bugly.crashreport.BuglyLog;

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
    private static final String TAG = Seedbank.class.getName();

    public Seedbank(int locationX, int locationY, int status,Bitmap bitmap)
    {
        super(locationX, locationY, status, bitmap);
       // BuglyLog.i(TAG, "location:  " + locationX + "  locationY:   " + locationY);
    }
    @Override
    public void drawSelf(Canvas canvas)
    {
        /** 绘制图案*/
        canvas.drawBitmap(mBitmap, this.locationX, this.locationY, null);
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
                    BuglyLog.i(TAG,"选中了");
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
