package beyondboy.scau.com.plantsvsz.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import beyondboy.scau.com.plantsvsz.model.Plan;
import beyondboy.scau.com.plantsvsz.model.Seedbank;
import beyondboy.scau.com.plantsvsz.model.Sun;
import beyondboy.scau.com.plantsvsz.model.Zombie;
import beyondboy.scau.com.plantsvsz.util.Config;

import static beyondboy.scau.com.plantsvsz.util.Config.cellHeight;
import static beyondboy.scau.com.plantsvsz.util.Config.cellWidth;
import static beyondboy.scau.com.plantsvsz.util.Config.plantPoints;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-01-30
 * Time: 10:14
 * 绘制游戏的界面
 */
public final class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    @SuppressWarnings("unused")
    private static final String TAG = GameView.class.getName();
    //对surface(显存)进行管理的对象
    private SurfaceHolder surfaceHolder;
    // 定义一个子线程
    private Thread thread;
    // 定义一个停止线程代码运行的变量
    private boolean runing;
    // 定义一个画笔
    private Paint paint;
    // 装面板上面的图片对象:向日葵,豌豆
    private LinkedList<Seedbank> seedbanks;
    // 装等待安放到跑道的图片对象:向日葵,豌豆
    private LinkedList<Seedbank> emplaces;
    // 装跑道的对象
    private SparseArray<Plan> plans;
    // 装跑道的阳光
    private List<Sun> suns;
    // 装跑道的子弹
    private LinkedList<Plan> bullets;
    // 装跑道的僵尸
    private LinkedList<Zombie> zombies;
    private static GameView gameView;
    public GameView(Context context)
    {
        super(context);
        // 获取父类提供的surfaceHolder
        surfaceHolder = getHolder();
        // 必须通过surfaceHolder加入对实现了SurfaceHolder.Callback进行监听:需要监听Surface的生命周期
        surfaceHolder.addCallback(this);
        gameView=this;
    }
    // 提供一个对外访问静态属性的方法
    public static GameView getInstanse()
    {
        return gameView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        init();
        //最好在Surface被创建的时候，开启绘图线程。因为没有创建Surface的话，绘制再多的东西也无法渲染到屏幕上。
        thread=new Thread(this);
        /**开始启动绘制*/
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    private void init()
    {
        runing=true;
        // 初始化画笔
        paint = new Paint();
        paint.setColor(Color.RED);
        seedbanks=new LinkedList<>();
        emplaces=new LinkedList<>();
        plans=new SparseArray<>();
        suns=new LinkedList<>();
        zombies=new LinkedList<>();
        Bitmap bitmap=Config.seedbankBitmap;
        bullets=new LinkedList<>();
        // BuglyLog.i(TAG, "数值计算："+(4+8<<1));
        // 计算面板上面图片的宽度
        int sbkimWidth=bitmap.getWidth()/7;
        Seedbank flower=new Seedbank(Config.seedbankX+sbkimWidth,0,Seedbank.SEED_FLOWER,Config.seedFlowerBitmap);
        Seedbank pea=new Seedbank(Config.seedbankX+(sbkimWidth<<1),0,Seedbank.SEED_PEA,Config.seedPeaBitmap);
        seedbanks.add(flower);
        seedbanks.add(pea);
    }

  /*  *//**绘制面板图案*//*
    private Seedbank drawSeedbank(int locationX, int locationY, int status,WeakReference<Bitmap> bitmapWeakReference)
    {
        Bitmap bitmap=bitmapWeakReference.get();
        if(bitmap!=null)
        {
            return new Seedbank(locationX,locationY,status,bitmap);
        }
        return null;
    }*/
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        runing=false;
        if(thread!=null)
        {
            thread.interrupt();
            thread=null;
        }
        surfaceHolder.removeCallback(this);
    }

    @Override
    public void run()
    {
        Canvas canvas;
        SurfaceHolder holder=this.surfaceHolder;
        while (runing&&holder!=null)
        {
            // 获取内存的画布
            canvas=holder.lockCanvas();
            //先把图案绘制到内存中
            render(canvas);
            //一定要执行:绘制的内容结束,提交至屏幕进行绘制
            if(canvas!=null)//防止屏幕旋转或崩溃导致canvas为空
                holder.unlockCanvasAndPost(canvas);
            // 动画的产生的原因:1秒钟有24帧
            try
            {
                Thread.sleep(1000 /40);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
                //CrashReport.postCatchedException(e);
            }
        }
    }

    /**一直在子线程运行用来绘图案*/
    private void render(Canvas canvas)
    {
        if(canvas!=null)
        {
            //需要绘制内容之前,主要用来清屏:绘制有背景颜色的矩形,绘制背景图片
            canvas.drawBitmap(Config.bkBitmap,0,0,null);
            //BuglyLog.i(TAG, "背景图像是否为空：  "+Config.bkBitmap.get());
            // 绘制面板
            canvas.drawBitmap(Config.seedbankBitmap,Config.seedbankX,0,null);
            // 绘制面板上面的图片
            for(Seedbank seedbank:seedbanks)
            {
                seedbank.drawSelf(canvas);
            }
            paint.setTextSize(50.f);
            // 绘制面板上面的分值
            canvas.drawText(String.valueOf(Config.totalScore), Config.seedbankX+10 + cellWidth / 6, (int) (cellHeight * 0.83), paint);
            // 绘制跑道的单元格线
            /*for (int i = 0; i < 5; i++)
            {
                int y=(int)(cellHeight*0.85)+i*cellHeight;
                int startx=0;
                canvas.drawLine(startx,y,Config.SCREENINFO.x,y,paint);
            }
            for (int j = 0; j <9 ; j++)
            {
                int x=(int)(cellHeight*1.5)+j*cellWidth;
                int starty=0;
                canvas.drawLine(x,starty,x,Config.SCREENINFO.y,paint);
            }*/
           // BuglyLog.i(TAG, "容量：   "+plans.size());
            // 绘制放在跑道对象的图片
            for (int i = 0; i < plantPoints.size(); i++)
            {
                Plan plan=plans.get(i);
               // BuglyLog.i(TAG, "是否为空：  "+plans.get(i)+"   索引：  "+i);
                if(plan!=null)
                {
                    plan.drawSelf(canvas);
                }
            }
            // 绘制阳光的图片
            for (Iterator<Sun> iterator = suns.iterator(); iterator.hasNext();)
            {
                Sun sun = iterator.next();
                // 阳光已经死亡
                if (sun.isDead())
                {
                    iterator.remove();
                } else
                {
                    sun.drawSelf(canvas);
                }
            }
            // 绘制子弹的图片
            for (Iterator<Plan> iterator = bullets.iterator(); iterator.hasNext();)
            {
                Plan bullet = iterator.next();
                // 子弹已经死亡
                if (bullet.isDead())
                {
                    iterator.remove();
                } else
                {
                    bullet.drawSelf(canvas);
                }
            }
            // 绘制僵尸的图片
            for(Iterator<Zombie> iterator=zombies.iterator();iterator.hasNext();)
            {
                Zombie zombie=iterator.next();
                //僵尸已经死亡
                if (zombie.isDead())
                {
                    iterator.remove();
                } else
                {
                    zombie.drawSelf(canvas);
                }
            }
            // 绘制等待安放到跑道的图片
            for (Seedbank seedbank : emplaces)
            {
                seedbank.drawSelf(canvas);
            }
            addZombie();
        }
    }
   /* private void drawBitmap(WeakReference<Bitmap> bitmapWeakReference,Canvas canvas,int x,int y,Paint paint)
    {
        Bitmap bitmap=bitmapWeakReference.get();
        if(bitmap!=null)
            canvas.drawBitmap(bitmap,x,y,paint);
    }
    private void drawBitmap(WeakReference<Bitmap> bitmapWeakReference,Canvas canvas)
    {
        drawBitmap(bitmapWeakReference,canvas,0,0,null);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        for(Sun sun:suns)
        {
            if(sun.onTouch(event))
            {
                return  true;
            }
        }
        // 处理等待安放到跑道的对象的触屏事件
        for (Seedbank seedbank : emplaces)
        {
            //被触屏了
            if(seedbank.onTouch(event))
            {
                return true;
            }
        }
        // 处理面板上面对象的触屏事件
        for(Seedbank seedbank:seedbanks)
        {
            //被触屏了
            if(seedbank.onTouch(event))
            {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
    // 把面板对象变成等待安放到跑道的对象:
    public void addEmplace(Seedbank seedbank)
    {
        if(emplaces.size()>0)
        {
            return;
        }
        if(seedbank.getStates()==Seedbank.SEED_FLOWER)
        {
            emplaces.add(new Seedbank(seedbank.getLocationX(),seedbank.getLocationY(),Seedbank.EMPLACE_FLOWER));
        }
        else if(seedbank.getStates()==Seedbank.SEED_PEA)
        {
            emplaces.add(new Seedbank(seedbank.getLocationX(),seedbank.getLocationY(),Seedbank.EMPLACE_PEA));
        }
        else
        {
            throw new RuntimeException("没有找到对应面板的对象:" + seedbank.getStates());
        }
    }
    // 把等待安放状态的对象变成跑道的对象
    public void addPlan(Seedbank seedbank)
    {
        for (int i = 0; i < plantPoints.size(); i++)
        {
            Point point=plantPoints.get(i);
            if(plans.get(i)!=null)
            {
                //BuglyLog.i(TAG, "同一个位置不能再放：   "+i+"   是否为空：  "+plans.get(i));
                continue;
            }
            // 在跑道里面寻找合适的位置进行安放
            int x=Math.abs(point.x-seedbank.getLocationX());
            int y=Math.abs(point.y - seedbank.getLocationY());
            // 找到合适位置之后,当前等待安放状态的对象的坐标位置还必须调整:单元格的坐标
            if(x<cellWidth>>1&&y<cellHeight>>1)
            {

                if(seedbank.getStates()==Seedbank.EMPLACE_FLOWER)
                {
                    plans.put(i,new Plan(point.x, point.y, Plan.PLAN_FLOWER));
                }
                else if(seedbank.getStates()==Seedbank.EMPLACE_PEA)
                {
                    plans.put(i,new Plan(point.x, point.y, Plan.PLAN_PEA));
                }
                else
                {
                    throw new RuntimeException("没有找到对应等待安放状态的对象:" + seedbank.getStates());
                }
                emplaces.clear();
                break;
            }
        }
    }

    // 跑道里面的向日葵在一定的时间范围内产生阳光
    public void addSun(Plan plan)
    {
        suns.add(new Sun(plan.getLocationX(), plan.getLocationY(), Sun.SHOW));
    }

    public void addBullet(Plan plan)
    {
        bullets.add(new Plan(plan.getLocationX(),plan.getLocationY(),Plan.PLAN_BULLET));
    }

    // 通过僵尸的y坐标找到索引
    private static int getIndex(Zombie zombie)
    {
        for (int i = 0; i <Config.raceWayYpoints.length; i++)
        {
            if (Config.raceWayYpoints[i] == zombie.getLocationY())
            {
                return i;
            }
        }
        return 0;
    }
    // 间隔一定的时间随机产生僵尸
    private static Random random = new Random();
    private long createZombieTime;
    private void addZombie()
    {
        long nowTime=System.currentTimeMillis();
        if(nowTime-createZombieTime>Config.createZombie)
        {
            createZombieTime=nowTime;
            // 随机数0-4
            int index=random.nextInt(5);
            zombies.add(new Zombie(Config.SCREENINFO.x,Config.raceWayYpoints[index],0));
        }
    }
}
