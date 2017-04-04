package com.yieti.fastload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hoooou on 17/4/2.
 */

public class DownUtils {
    /**
     * 根据url下载图片在指定的文件
     *
     * @param file
     * @param urlStr
     * @return
     */
    public static boolean downloadImgByUrl(String urlStr, File file) {
        FileOutputStream fos = null;
        BufferedInputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            is.mark(conn.getContentLength());
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(is, null, opts);
            //获取imageview想要显示的宽和高
//            ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(imageview);
//            opts.inSampleSize = ImageUtils.caculateInsampleSize(opts,
//                    imageViewSize.width, imageViewSize.heigh);
//
//            opts.inJustDecodeBounds = false;
//
//            is.reset();
//          Bitmap  bitmap = BitmapFactory.decodeStream(is, null, opts);
            int size = 2048;
            byte[] data = new byte[2048];
            while ((size=is.read(data))!=-1) {
                fileOutputStream.write(data,0,size);
            }
            fileOutputStream.close();
            conn.disconnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }

    }

    public static Bitmap downloadImgByUrl(String urlStr, ImageView imageview) {
        FileOutputStream fos = null;
        BufferedInputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());

            is.mark(conn.getContentLength());
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, opts);
            //获取imageview想要显示的宽和高
            ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(imageview);
            opts.inSampleSize = ImageUtils.caculateInsampleSize(opts,
                    imageViewSize.width, imageViewSize.heigh);

            opts.inJustDecodeBounds = false;

            is.reset();
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);
            conn.disconnect();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }

        return null;

    }
}
