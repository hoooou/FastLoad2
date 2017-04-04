package com.yieti.fastload;

import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hoooou on 17/4/2.
 */

class ImageUtils {
    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }
    public static ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int width=imageView.getWidth();
        if(width<=0){
            width= layoutParams.width;
        }
        if (width <= 0) {
            width=getImageViewFieldValue(imageView,"mMaxWidth");
        }
        if (width <= 0) {
            width=displayMetrics.widthPixels;
        }
        int height=imageView.getHeight();
        if(height<=0){
            height= layoutParams.height;
        }if(height<=0){
            height= getImageViewFieldValue(imageView,"mMaxHeight");

        }if(height<=0){
            height=displayMetrics.heightPixels;
        }
        imageSize.width=width;
        imageSize.heigh=height;
        return imageSize;
    }

    private static int getImageViewFieldValue(ImageView imageView, String name) {
        Class<ImageView> imageViewClass = ImageView.class;
        try {
            Field field = imageViewClass.getField(name);
            field.setAccessible(false);
            int width = field.getInt(imageView);
            return width;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int caculateInsampleSize(BitmapFactory.Options options, int reqWidth, int reqHeigh) {
        int wid=options.outWidth;
        int height = options.outHeight;
        int inSampleSize=1;
        if (wid > reqWidth || height > reqHeigh) {
            int widthRadio = Math.round(wid * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeigh);
            inSampleSize=Math.max(widthRadio,heightRadio);
        }
        return inSampleSize;
    }

    public static class ImageSize {
        public int width;
        public int heigh;
    }
}
