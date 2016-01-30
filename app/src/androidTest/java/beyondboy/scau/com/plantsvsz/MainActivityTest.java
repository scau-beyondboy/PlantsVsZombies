package beyondboy.scau.com.plantsvsz;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-01-31
 * Time: 01:58
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testRotateScreen()
    {
        for (int i = 0; i < 10000000; i++)
        {
            rotateScreen();
        }
    }
    /**改变屏幕*/
    private void rotateScreen()
    {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;
        int direction=0;
        if(orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            System.out.println("变为横向");
            direction=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ;
        }
        else
        {
            System.out.println("变为竖向");
            direction=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(direction);
    }
}
