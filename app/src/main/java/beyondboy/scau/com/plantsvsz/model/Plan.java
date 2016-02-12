package beyondboy.scau.com.plantsvsz.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import beyondboy.scau.com.plantsvsz.util.Config;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-02-12
 * Time: 23:18
 * 跑道的对象:向日葵,豌豆,子弹
 */
public class Plan extends BaseModel
{
    // status==PLAN_FLOWER表示当前对象就是向日葵
    public static final int PLAN_FLOWER = 1;
    // status==PLAN_PEA表示当前对象就是豌豆
    public static final int PLAN_PEA = 2;
    //减慢对象摇摆速度
    private int slow=0;
    private int indexPea, indexFlower;

    public Plan(int locationX, int locationY, int status, Bitmap bitmap)
    {
        super(locationX, locationY, status, bitmap);
    }
    public Plan(int locationX, int locationY, int status)
    {
        this(locationX, locationY, status, Config.flowerBitmaps[0]);
    }

    @Override
    public void drawSelf(Canvas canvas)
    {
        if(states==PLAN_FLOWER)
        {
            canvas.drawBitmap(Config.flowerBitmaps[indexFlower],this.locationX,this.locationY,null);
        }
        else if(states==PLAN_PEA)
        {
            canvas.drawBitmap(Config.peaBitmaps[indexPea],this.locationX,this.locationY,null);
        }
        else
        {
            throw new RuntimeException("没有找到对应面板的对象:" + states);
        }
        if(slow==3)
        {
            indexFlower++;
            indexPea++;
            slow=0;
            if(indexFlower>7||indexPea>7)
            {
                indexFlower=0;
                indexPea=0;
            }
        }
        slow++;
    }
}
