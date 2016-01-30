package beyondboy.scau.com.plantsvsz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import beyondboy.scau.com.plantsvsz.util.Config;
import beyondboy.scau.com.plantsvsz.util.ImageUtils;
import beyondboy.scau.com.plantsvsz.view.GameView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        init();
        GameView gameView=new GameView(this);
        setContentView(gameView);
    }

    private void init()
    {
        /**获取背景图片*/
        Config.bkBitmap= ImageUtils.compressBitmap(R.drawable.bk, Config.SCREENINFO.x,Config.SCREENINFO.y);
    }
}
