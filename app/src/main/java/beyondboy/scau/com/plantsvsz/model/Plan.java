package beyondboy.scau.com.plantsvsz.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import beyondboy.scau.com.plantsvsz.util.Config;
import beyondboy.scau.com.plantsvsz.view.GameView;

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
    // status==PLAN_BULLET表示当前对象就是子弹
    public static final int PLAN_BULLET = 3;
    private static final String TAG = Plan.class.getName();
    //减慢对象摇摆速度
    private int slow=0;
    private int indexPea, indexFlower;
    private long createFlowerTime, createPeaTime;
    public Plan(int locationX, int locationY, int status, Bitmap bitmap)
    {
        super(locationX, locationY, status, bitmap);
        long nowTime=System.currentTimeMillis();
        if(status==PLAN_FLOWER)
        {
            this.createFlowerTime=nowTime;
        }
        else if(status==PLAN_PEA)
        {
            this.createPeaTime=nowTime;
        }
    }
    public Plan(int locationX, int locationY, int status)
    {
        this(locationX, locationY, status, Config.flowerBitmaps[0]);
    }

    @Override
    public void drawSelf(Canvas canvas)
    {
        if(canvas==null)return;
        long nowTime=System.currentTimeMillis();
        if(states==PLAN_FLOWER)
        {
            canvas.drawBitmap(Config.flowerBitmaps[indexFlower],this.locationX,this.locationY,null);
            // 在一定的时间范围内产生阳光
            if(nowTime-createFlowerTime>Config.createSunTime)
            {
                GameView.getInstanse().addSun(this);
                createFlowerTime=nowTime;
            }
        }
        else if(states==PLAN_PEA)
        {
            canvas.drawBitmap(Config.peaBitmaps[indexPea],this.locationX,this.locationY,null);
            // 在一定的时间范围内产生子弹
            if (nowTime - this.createPeaTime > Config.createBullet)
            {
                this.createPeaTime = nowTime;
                System.out.println("产生子弹");
                GameView.getInstanse().addBullet(this);
            }
        }
        // 跑道的子弹:向右移动
        else if(states==PLAN_BULLET)
        {
            // 向右移动超过了屏幕的宽度
            if(this.locationX>=Config.SCREENINFO.x)
            {
                this.lifeValue=0;
            }
            else
            {
                canvas.drawBitmap(Config.bulletBitmap,this.locationX,this.locationY,null);
                this.locationX+=Config.moveBulletX;
            }
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
