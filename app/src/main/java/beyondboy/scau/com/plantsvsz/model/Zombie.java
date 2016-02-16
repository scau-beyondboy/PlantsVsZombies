package beyondboy.scau.com.plantsvsz.model;

import android.graphics.Canvas;

import beyondboy.scau.com.plantsvsz.util.Config;
import beyondboy.scau.com.plantsvsz.view.GameView;

import static beyondboy.scau.com.plantsvsz.util.Config.moveZombieX;
import static beyondboy.scau.com.plantsvsz.util.Config.zombieBitmaps;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-02-16
 * Time: 00:00
 */
public class Zombie extends  BaseModel
{
    private int index = 0;

    public Zombie(int locationX, int locationY, int status)
    {
        super(locationX, locationY, status, Config.zombieBitmaps[0]);
    }
    @Override
    public void drawSelf(Canvas canvas)
    {
        // 僵尸从右边向左边的方向移动
        if (this.locationX <= 0)
        {
            this.lifeValue = 0;
        } else
        {
            canvas.drawBitmap(zombieBitmaps[index++], this.locationX, this.locationY, null);
            if (index >= zombieBitmaps.length)
            {
                index = 0;
            }
            this.locationX -= moveZombieX;
        }
        //由僵尸发起碰撞检测
        GameView.getInstanse().checkLife(this);
    }
}
