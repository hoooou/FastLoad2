package com.yieti.fastload;

import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int i = PermissionChecker.checkCallingOrSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        if (i != PermissionChecker.PERMISSION_GRANTED) {
                String[] strings = new String[]{"android.permission.READ_EXTERNAL_STORAGE","android.permission.INTERNET"};
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(strings,122);
            } else {
//                PermissionChecker.
                onRequestPermissionsResult(122,strings,null);
            }
        } else {
            init();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
init();
    }

        ArrayList<String> objects = new ArrayList<>();
    public void init() {
//        objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
//        objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
//        objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
// objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
//        objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
//        objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
// objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
//        objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
//        objects.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491066322870&di=78a79a64fdb8e49cfdc5f27437d0098b&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg");
//        objects.add("http://img2.dwstatic.com/www/1704/354983968984/1491029026840.jpg");
//        objects.add("http://pic123.nipic.com/file/20170307/4516668_142853064030_2.jpg");
//        objects.add("http://pic124.nipic.com/file/20170321/12173849_104100807038_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/7892613_142716152031_2.jpg");
//        objects.add("http://pic125.nipic.com/file/20170329/11680438_115810821000_2.jpg");
        if (objects.size()==0) {
            getSupportLoaderManager().initLoader(0, null, this);
            return;
        }
        GridView grid_view = (GridView) findViewById(R.id.grid_view);
        grid_view.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return objects.size();
            }

            @Override
            public String getItem(int position) {
                return objects.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = null;
//                if (convertView == null) {
                    imageView= new ImageView(MainActivity.this);
//                }else{
//                    imageView= (ImageView) convertView;
//                }
                String item = getItem(position);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.height = 300;
                layoutParams.width = 200;
                imageView.setLayoutParams(layoutParams);
//                FastImageLoad.with(MainActivity.this).load(item).into(imageView).error(R.mipmap.ic_launcher).defaultSrc();
                FastLoad2.getInstance().loadImage2(item,imageView, false);
                return imageView;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader cursorLoader = new CursorLoader(this, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);


        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String[] columnNames = data.getColumnNames();
//        LogUtils.write(Arrays.toString(columnNames));
//        data.moveToNext();
//        for (String value:coolumnNames) {
//            LogUtils.write(value+":"+data.getString(data.getColumnIndex(value)));
//        }
        for (int i=0;i<=1000;i++) {
            data.moveToNext();
            objects.add(data.getString(data.getColumnIndex("_data")));
        }
        init();
        data.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
