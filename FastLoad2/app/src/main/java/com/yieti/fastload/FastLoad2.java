package com.yieti.fastload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by hoooou on 17/4/2.
 */

public class FastLoad2 {
    private static Handler mUIHandler1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String url = msg.getData().getString("url");
            ImageView imageView = (ImageView) msg.obj;

            if (imageView.getTag().equals(url)) {
                Bitmap data = msg.getData().getParcelable("data");
                imageView.setImageBitmap(data);
            }
        }
    };
    private static Handler mUIHandler;
    private Type mType;
    private boolean isDiskCacheEnable=false;
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);

    public void loadImage2(String path, ImageView imageView, boolean isFromNet) {
        imageView.setTag(path);
        if (mUIHandler == null) {
            mUIHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    ImageView obj = (ImageView) msg.obj;
                    Bundle data = msg.getData();
                    String url = data.getString("url");
                    if (obj.getTag().toString().equals(url)) {
                        Bitmap data1 = (Bitmap) data.getParcelable("data");
                        obj.setImageBitmap(data1);
                    }   }
            };
        }
        Bitmap bitmap = getBitmapFromLruCache(path);
        if (bitmap != null) {
            refreshBitmap(path, imageView, bitmap);
        } else {
            addTask(buildTask(path, imageView, isFromNet));
        }
    }

    private Runnable buildTask(final String path, final ImageView imageView, final boolean isFromNet) {

        return new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                if (isFromNet) {
                    File file = getDiskCacheDir(imageView.getContext(), ImageUtils.stringToMD5(path));
                    if (file.exists()) {
                        bitmap = loadImageFromDisk(imageView, file.getAbsolutePath());
                    }
                    if (!file.exists()) {
                        if (isDiskCacheEnable) {
                            boolean downState = DownUtils.downloadImgByUrl(path, file);
                            if (downState) {
                                bitmap = loadImageFromDisk(imageView, file.getAbsolutePath());
                            }
                        }else{
                            bitmap=DownUtils.downloadImgByUrl(path,imageView);
                        }
                    }
                } else {
                    bitmap = loadImageFromDisk(imageView, path);

                }
                addBitmapToLruCache(path, bitmap);
                refreshBitmap(path, imageView, bitmap);
                mSemaphoreThreadPool.release();
            }
        };
    }

    /**
     * 获得缓存图片的地址
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName)
    {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()))
        {
            cachePath = context.getExternalCacheDir().getPath();
        } else
        {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private void addBitmapToLruCache(String path, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (getBitmapFromLruCache(path) == null) {
            mLruCache.put(path, bitmap);
        }
    }

    public synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        if (mPoolThreadHandler == null) {
            try {
                mSemaphorePoolThreadHandler.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    private void refreshBitmap(String path, ImageView imageView, Bitmap bitmap) {

        Message msg = Message.obtain();
        msg.obj = imageView;
        Bundle bundle = new Bundle();
        bundle.putString("url", path);
        bundle.putParcelable("data", bitmap);
        msg.setData(bundle);
        mUIHandler.sendMessage(msg);    }
    private class ImgBeanHolder
    {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }
    private Bitmap getBitmapFromLruCache(String path) {
        Bitmap bitmap = mLruCache.get(path);
        return bitmap;
    }

    public void loadImage(ImageView imageView, String url) {
        imageView.setTag(url);
        imageView.setImageResource(R.mipmap.ic_launcher);
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("资源链接不可为空");
        }
        File file = new File(url);
        if (file.exists()) {
            Bitmap bitmap = loadImageFromDisk(imageView, url);
            Message obtain = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", bitmap);
            bundle.putString("url", url);
            obtain.setData(bundle);
            obtain.obj = imageView;
            mUIHandler.sendMessage(obtain);
        }
        if (url.contains("http")) {
            loadImageFromNet(imageView, url);
        }

    }

    private static void loadImageFromNet(final ImageView imageView, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(imageView);
                Bitmap bitmap = DownUtils.downloadImgByUrl(url, imageView);
                Message obtain = Message.obtain();
                obtain.obj = imageView;
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putParcelable("data", bitmap);
                obtain.setData(bundle);
                mUIHandler.sendMessage(obtain);
            }
        }).start();

    }

    private static Bitmap downUrlImg(ImageUtils.ImageSize imageViewSize, String url) {
        try {
            URL url1 = new URL(url);

            HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            bufferedInputStream.mark(bufferedInputStream.available());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bufferedInputStream, null, options);
            options.inSampleSize = ImageUtils.caculateInsampleSize(options, imageViewSize.width, imageViewSize.heigh);
            bufferedInputStream.reset();
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            bufferedInputStream.close();
            urlConnection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap loadImageFromDisk(ImageView imageView, String url) {
        Bitmap bitmapFromLruCache = getBitmapFromLruCache(url);
        if (bitmapFromLruCache != null) {
            return bitmapFromLruCache;
        }
        ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(imageView);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url, options);
        options.inSampleSize = ImageUtils.caculateInsampleSize(options, imageViewSize.width, imageViewSize.heigh);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(url, options);
        return bitmap;
    }

    private FastLoad2(int threadCount, Type type) {
        init(threadCount, type);

    }

    ExecutorService mThreadPool;
    LinkedList<Runnable> mTaskQueue;
    Semaphore mSemaphoreThreadPool;
    LruCache<String, Bitmap> mLruCache;

    private void init(int threadCount, Type type) {
        initBackThread();
        //创建内存lrucache
        //获取应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;

        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
        this.mType = type;
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<>();
        mSemaphoreThreadPool = new Semaphore(threadCount);


    }

    Thread mPoolThread;
    Handler mPoolThreadHandler;

    private void initBackThread() {
        //轮询线程
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };
        mPoolThread.start();

    }

    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTaskQueue.removeLast();
        }
        return null;
    }

    static FastLoad2 instance;

    public static FastLoad2 getInstance() {
        if (instance == null) {
            synchronized (FastLoad2.class) {
                if (instance == null) {
                    instance = new FastLoad2(3, Type.LIFO);
                }
            }
        }
        return instance;
    }

    public enum Type {
        FIFO, LIFO;
    }
}

