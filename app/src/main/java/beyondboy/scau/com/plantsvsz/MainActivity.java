package beyondboy.scau.com.plantsvsz;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.bugly.crashreport.BuglyLog;

import java.lang.ref.WeakReference;

import beyondboy.scau.com.plantsvsz.util.Config;
import beyondboy.scau.com.plantsvsz.util.ImageUtils;
import beyondboy.scau.com.plantsvsz.view.GameView;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = MainActivity.class.getName();
    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        init();
        mGameView = new GameView(this);
        setContentView(mGameView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mGameView.onTouchEvent(event);
    }

    private void init()
    {
        Resources resources = getResources();
        Bitmap bitmap;
        // 缩放背景图片
        bitmap= BitmapFactory.decodeResource(resources, R.drawable.bk);
        Config.scaleWidth=(float)Config.SCREENINFO.x/bitmap.getWidth();// 缩小
        Config.scaleHeight=(float)Config.SCREENINFO.y/bitmap.getHeight();
        /**获取背景图片*/
        bitmap= ImageUtils.resizeBitmap(bitmap, Config.SCREENINFO.x, Config.SCREENINFO.y);
        Config.bkBitmap= new WeakReference<>(bitmap);
        //获取面板图片
        bitmap=ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources,R.drawable.seedbank));
        Config.seedbankBitmap= new WeakReference<>(bitmap);
        // 处理面板以及面板的上面图片
        bitmap= ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.seedbank));
        // 计算面板绘制的x坐标
        Config.seedbankX = (Config.SCREENINFO.x - bitmap.getWidth()) / 2;
        BuglyLog.i(TAG, "面板的x坐标："+Config.seedbankX);
        //获取面板高度和宽度
        int sbHeight=bitmap.getHeight();
        int sbWidth=bitmap.getWidth();
        Config.seedbankBitmap= new WeakReference<>(bitmap);
        // 面板上面的图片宽度:面板图片宽度1/7,高度=面板图片高度
        bitmap= ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.seed_flower), sbWidth/ 7,
                sbHeight);
        Config.seedFlowerBitmap= new WeakReference<>(bitmap);
        bitmap = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.seed_pea), sbWidth/ 7,
                sbHeight);
        Config.seedPeaBitmap= new WeakReference<>(bitmap);
    }
}
