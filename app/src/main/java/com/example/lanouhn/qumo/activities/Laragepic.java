package com.example.lanouhn.qumo.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lanouhn.qumo.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by lanouhn on 2016/8/19.
 */
public class Laragepic extends Activity {
    private final static String TAG = "IcsTestActivity";
    //SD卡的存放位置
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";

    private ProgressDialog mSaveDialog = null;//进度提示
    private Bitmap mBitmap;
    private String mFileName;//定义的文件名
    private String mSaveMessage;
    private ImageView pic;//下载按钮
    private ImageView share;//分享按钮
    private ImageView image;
    private String  url;//接收传过来的图片地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laragepic);
        image= (ImageView) findViewById(R.id.image);
        pic= (ImageView) findViewById(R.id.pic);
        share= (ImageView) findViewById(R.id.share);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        Picasso.with(Laragepic.this).load(url).into(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//点击返回上一页面
            }
        });
        //分享按钮
     share.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             showShare();//分享
         }
     });


        new Thread(connectNet).start();

        // 下载图片
        pic.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                mSaveDialog = ProgressDialog.show(Laragepic.this, "保存图片", "图片正在保存中，请稍等...", true);
                new Thread(saveFileRunnable).start();
            }
        });
    }

    /**
     * Get image from newwork
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public byte[] getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get image from newwork
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }
    /**
     * Get data from stream
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
//保存图片
    private Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
                saveFile(mBitmap, mFileName);
                mSaveMessage = "图片保存成功！";
            } catch (IOException e) {
                mSaveMessage = "图片保存失败！";
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }

    };

    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            mSaveDialog.dismiss();
            Log.d(TAG, mSaveMessage);
            Toast.makeText(Laragepic.this, mSaveMessage, Toast.LENGTH_SHORT).show();
        }
    };

    /*
     * 连接网络
     * 由于在4.0中不允许在主线程中访问网络，所以需要在子线程中访问
     */
    private Runnable connectNet = new Runnable(){
        @Override
        public void run() {
            try {
                String filePath = url;
                mFileName = "test.jpg";

                //以下是取得图片的两种方法
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap
                byte[] data = getImage(filePath);
                if(data!=null){
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
                }else{
                    Toast.makeText(Laragepic.this, "Image error!", Toast.LENGTH_SHORT).show();
                }
                ////////////////////////////////////////////////////////

                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/
                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
                //********************************************************************/

                // 发送消息，通知handler在主线程中更新UI
                // connectHanlder.sendEmptyMessage(0);
                Log.d(TAG, "set image ...");
            } catch (Exception e) {
                Toast.makeText(Laragepic.this,"无法链接网络！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }

    };
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
       // oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用

        //把要分享的东西替换成自己的！！！！！！
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("好图不藏私");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("这TM真有意思");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(this);
    }
}

