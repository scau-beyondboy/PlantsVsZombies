package beyondboy.scau.com.plantsvsz;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.bugly.crashreport.BuglyLog;

import beyondboy.scau.com.plantsvsz.util.Config;
import beyondboy.scau.com.plantsvsz.util.ImageUtils;
import beyondboy.scau.com.plantsvsz.view.GameView;
import static beyondboy.scau.com.plantsvsz.util.Config.*;

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
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inMutable=true;
        options.inDensity=Config.DENSITYDPI;
        // 缩放背景图片
        bitmap= BitmapFactory.decodeResource(resources, R.drawable.bk,options);
        Config.scaleWidth=(float)Config.SCREENINFO.x/bitmap.getWidth();// 缩小
        Config.scaleHeight=(float)Config.SCREENINFO.y/bitmap.getHeight();
        /**获取背景图片*/
        Config.bkBitmap= ImageUtils.resizeBitmap(bitmap, Config.SCREENINFO.x, Config.SCREENINFO.y);
        //Config.bkBitmap= new WeakReference<>(bitmap);
        //获取面板图片
        bitmap=ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources,R.drawable.seedbank,options));
        // 处理面板以及面板的上面图片
        Config.seedbankBitmap= bitmap;
        // 计算面板绘制的x坐标
        Config.seedbankX = (Config.SCREENINFO.x - bitmap.getWidth()) / 2;
        BuglyLog.i(TAG, "面板的x坐标："+Config.seedbankX);
        //获取面板高度和宽度
        int sbHeight=bitmap.getHeight();
        int sbWidth=bitmap.getWidth();
       // Config.seedbankBitmap= bitmap;
        // 面板上面的图片宽度:面板图片宽度1/7,高度=面板图片高度
        bitmap= ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.seed_flower,options), sbWidth/ 7,
                sbHeight);
        Config.seedFlowerBitmap=bitmap;
        bitmap = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.seed_pea,options), sbWidth/ 7,
                sbHeight);
        Config.seedPeaBitmap= bitmap;
        // 跑道图片的初始化
        flowerBitmaps[0] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_01,options),sbWidth/ 7,
                sbHeight);
        flowerBitmaps[1] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_02,options),sbWidth/ 7,
                sbHeight);
        flowerBitmaps[2] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_03,options),sbWidth/ 7,
                sbHeight);
        flowerBitmaps[3] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_04,options),sbWidth/ 7,
                sbHeight);
        flowerBitmaps[4] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_05,options),sbWidth/ 7,
                sbHeight);
        flowerBitmaps[5] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_06,options),sbWidth/ 7,
                sbHeight);
        flowerBitmaps[6] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_07,options),sbWidth/ 7,
                sbHeight);
        flowerBitmaps[7] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_1_08,options),sbWidth/ 7,
                sbHeight);

        peaBitmaps[0] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_01,options),sbWidth/ 7,
                sbHeight);
        peaBitmaps[1] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_02,options),sbWidth/ 7,
                sbHeight);
        peaBitmaps[2] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_03,options),sbWidth/ 7,
                sbHeight);
        peaBitmaps[3] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_04,options),sbWidth/ 7,
                sbHeight);
        peaBitmaps[4] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_05,options),sbWidth/ 7,
                sbHeight);
        peaBitmaps[5] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_06,options),sbWidth/ 7,
                sbHeight);
        peaBitmaps[6] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_07,options),sbWidth/ 7,
                sbHeight);
        peaBitmaps[7] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.p_2_08,options),sbWidth/ 7,
                sbHeight);
        // 跑道单元格的宽高
        // 左侧留1.5个单元格,跑道有9列,右侧0.5个单元格
        cellWidth = SCREENINFO.x / 11;
        // 顶部留0.85个单元格,跑道有5行,底部0.5个单元格
        cellHeight =SCREENINFO.y/ 6;
        plantPoints=new SparseArray<>();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                int key=i*9+j;
                int x=(int)(cellWidth*1.5)+j*cellWidth;
                int y=(int)(cellHeight*0.85)+i*cellHeight;
                plantPoints.put(key,new Point(x,y));
                if(j==0)
                {
                    raceWayYpoints[i]=(int) (cellHeight * (0.3 + i));
                }
            }
        }
        sunBitmap=ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources,R.drawable.sun,options),sbWidth/ 7,
                sbHeight);
        bulletBitmap=ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources,R.drawable.bullet,options));
        zombieBitmaps[0] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.z_1_01,options));
        zombieBitmaps[1] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.z_1_02,options));
        zombieBitmaps[2] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.z_1_03,options));
        zombieBitmaps[3] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.z_1_04,options));
        zombieBitmaps[4] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.z_1_05,options));
        zombieBitmaps[5] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.z_1_06,options));
        zombieBitmaps[6] = ImageUtils.resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.z_1_07,options));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        GameView.getInstanse().destroyDrawingCache();
        GameView.getInstanse().stopRun();
    }
}
