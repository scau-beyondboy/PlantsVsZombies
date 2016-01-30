package beyondboy.scau.com.plantsvsz;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-01-30
 * Time: 09:23
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        CrashReport.initCrashReport(this, "900019200", false);
        CrashReport.isDebug=true;
    }
}
