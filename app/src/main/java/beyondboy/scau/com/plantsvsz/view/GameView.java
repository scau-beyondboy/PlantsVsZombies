package beyondboy.scau.com.plantsvsz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Future;

import beyondboy.scau.com.plantsvsz.util.Config;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-01-30
 * Time: 10:14
 * 绘制游戏的界面
 */
public final class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    //对surface(显存)进行管理的对象
    private SurfaceHolder surfaceHolder;
    private Future call;
    // 定义一个子线程
    private Thread thread;
    // 定义一个停止线程代码运行的变量
    private boolean runing;
    // 定义一个画笔
    private Paint paint;
    public GameView(Context context)
    {
        super(context);
        // 获取父类提供的surfaceHolder
        surfaceHolder = getHolder();
        // 必须通过surfaceHolder加入对实现了SurfaceHolder.Callback进行监听:需要监听Surface的生命周期
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        runing=true;
        // 初始化画笔
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        //最好在Surface被创建的时候，开启绘图线程。因为没有创建Surface的话，绘制再多的东西也无法渲染到屏幕上。
        thread=new Thread(this);
        /**开始启动绘制*/
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        runing=false;
        if(thread!=null)
        {
            thread.interrupt();
        }
    }

    @Override
    public void run()
    {
        Canvas canvas=null;
        SurfaceHolder holder=this.surfaceHolder;
        while (runing)
        {
            // 获取内存的画布
            canvas=holder.lockCanvas();
            //先把图案绘制到内存中
            render(canvas);
            //一定要执行:绘制的内容结束,提交至屏幕进行绘制
            holder.unlockCanvasAndPost(canvas);
            // 动画的产生的原因:1秒钟有24帧
            try
            {
                Thread.sleep(1000 / 40);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
                //CrashReport.postCatchedException(e);
            }
        }
    }

    private void render(Canvas canvas)
    {
        // 需要绘制内容之前,清屏
        //paint.setColor(Color.BLACK);
        //canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        if(canvas!=null)
        {
            canvas.drawBitmap(Config.bkBitmap,0,0,null);
        }
    }
}
