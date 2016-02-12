package beyondboy.scau.com.plantsvsz.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.tencent.bugly.crashreport.BuglyLog;


/**
 * Author:beyondboy
 * Gmail:xuguoli.scau@gmail.com
 * Date: 2016-01-30
 * Time: 14:01
 * 图片压缩和获取图片信息工具
 */
public final class ImageUtils
{
    private static final String TAG = ImageUtils.class.getName();

    /**根据一般是压缩图片，但如果图片比目标尺寸要小，则会图片放大到目标尺寸*/
    @SuppressWarnings("unused")
    public  static Bitmap compressBitmap(@DrawableRes int imageId,final int targetw,final int targeth)
    {
        Context context=Config.APPCONTEXT;
        BitmapFactory.Options imageOptions= new BitmapFactory.Options();
        imageOptions.inJustDecodeBounds=true;
        //获取图片信息,不加载内存
        BitmapFactory.decodeResource(context.getResources(), imageId, imageOptions);
        imageOptions.inMutable=true;
        imageOptions.inJustDecodeBounds=false;
        //如果手机像素大于160，那么设置该信息后，图片的宽度和高度就不会被自动加倍
        imageOptions.inDensity=Config.DENSITYDPI;
       // imageOptions.inTargetDensity=Config.DENSITYDPI;
       // imageOptions.inPreferredConfig=
        imageOptions.inSampleSize=calculateInSampleSize(imageOptions.outWidth,imageOptions.outHeight,targetw,targeth);
        //压缩图片
        Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), imageId, imageOptions);
        //如果实际图片比屏幕小，则调用下面方法放大图片否则就根据比例缩小
        if(imageOptions.inSampleSize==1||imageOptions.inSampleSize==0)
        {
            bitmap=resizeBitmap(bitmap,targetw,targeth);
        }
        /**共享图片内存*/
        imageOptions.inBitmap=bitmap;
        BuglyLog.i(TAG,"压缩图片的宽度和高度：　"+bitmap.getWidth()+"    "+bitmap.getHeight());
        BuglyLog.i(TAG, "图片的内存： " + bitmap.getRowBytes() * bitmap.getHeight() / 1024 / 1024);
        return bitmap;
    }

    /**计算压缩比例*/
    public static int calculateInSampleSize(final int srcw,final int srch,final int targetw,final int targeth)
    {
        int inSampleSize;
        // 计算出实际宽度和目标宽度的比率
        inSampleSize=Math.round((srcw / targetw + srch / targeth)>>2);
        BuglyLog.i(TAG, "原来宽度和高度："+srcw+"   "+srch+"  "+"目标宽度和高度:   "+targetw+"   "+targeth+"   缩放比例：  " + inSampleSize);
        return inSampleSize;
    }

    /**
     * 把传入的图片按照当前手机屏幕的大小进行横纵向比例缩放
     */
    public static Bitmap resizeBitmap(@NonNull Bitmap bitmap, int w, int h)
    {
        // 获取传入图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if ((w != 0 && h != 0))
        {
            // 计算出缩放比例
            float scaleWidth = ((float) w) / width;// =320/600=0.53
            float scaleHeight = ((float) h) / height;// 480/900=0.53
            Matrix matrix = new Matrix();
            // 注意中间的参数，如果x>1的话，那么就是放大，如果x<1的话，就是缩小
            matrix.postScale(scaleWidth, scaleHeight);// 缩放的修正
            Bitmap handlerBitmap= Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            BuglyLog.i(TAG, "图片的内存： " + handlerBitmap.getRowBytes() * bitmap.getHeight() / 1024+"k");
            return handlerBitmap;
        }
        else if(w == 0 && h == 0)
        {
            Matrix matrix = new Matrix();
            // 注意中间的参数，如果x>1的话，那么就是放大，如果x<1的话，就是缩小
            matrix.postScale(Config.scaleWidth,Config.scaleHeight);// 缩放的修正
            Bitmap handlerBitmap= Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            BuglyLog.i(TAG, "图片的内存： " + handlerBitmap.getRowBytes() * bitmap.getHeight() / 1024 +"k");
            return handlerBitmap;
        }
        else
        {
            throw new RuntimeException("传入的bitmap==null");
        }
    }

    /**
     * 默认根据{@link Config#scaleWidth}和{@link Config#scaleHeight}来缩放图片
     */
    public static Bitmap resizeBitmap(@NonNull Bitmap bitmap)
    {
        return resizeBitmap(bitmap,0,0);
    }
}
