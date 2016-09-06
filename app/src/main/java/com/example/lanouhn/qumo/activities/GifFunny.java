package com.example.lanouhn.qumo.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.utils.CommonUtil;
import com.example.lanouhn.qumo.utils.GifHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lanouhn on 2016/8/31.
 */
public class GifFunny extends Activity{
    private PlayGifTask mGifTask;
    ImageView iv;
    GifHelper.GifFrame[] frames;
    FileInputStream fis = null;
    MediaPlayer mpMediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //  setContentView(R.layout.giffunny);
        setContentView(iv, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT));
        //对Gif图片进行解码
        final InputStream fis = getResources().openRawResource(R.raw.hahaxiao);

        /*try {
            fis = new FileInputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"/tmp/111.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        frames = CommonUtil.getGif(fis);
        mGifTask = new PlayGifTask(iv, frames);
        mGifTask.startTask();
        Thread th = new Thread(mGifTask);
        th.start();
        initMusicplay();//音乐播放
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//关闭当前页
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mGifTask) mGifTask.stopTask();
        mpMediaPlayer.stop();
    }

    //用来循环播放Gif每帧图片
    private class PlayGifTask implements Runnable {
        int i = 0;
        ImageView iv;
        GifHelper.GifFrame[] frames;
        int framelen,oncePlayTime=0;

        public PlayGifTask(ImageView iv, GifHelper.GifFrame[] frames) {
            this.iv = iv;
            this.frames = frames;

            int n=0;
            framelen=frames.length;
            while(n<framelen){
                oncePlayTime+=frames[n].delay;
                n++;
            }
            Log.d("msg", "playTime= "+oncePlayTime);  //Gif图片单次播放时长

        }

        Handler h2=new Handler(){
            public void handleMessage(android.os.Message msg) {
                switch(msg.what){
                    case 1:
                        iv.setImageBitmap((Bitmap)msg.obj);
                        break;
                }
            };
        };



        @Override
        public void run() {
            if (!frames[i].image.isRecycled()) {
                //      iv.setImageBitmap(frames[i].image);
                android.os.Message m= android.os.Message.obtain(h2, 1, frames[i].image);
                m.sendToTarget();
            }
            iv.postDelayed(this, frames[i++].delay);
            i %= framelen;
        }

        public void startTask() {
            iv.post(this);
        }

        public void stopTask() {
            if(null != iv) iv.removeCallbacks(this);
            iv = null;
            if(null != frames) {
                for(GifHelper.GifFrame frame : frames) {
                    if(frame.image != null && !frame.image.isRecycled()) {
                        frame.image.recycle();
                        frame.image = null;
                    }
                }
                frames = null;
                //      mGifTask=null;
            }
        }
    }

    //音乐播放
    private void initMusicplay() {
        //下边的代码直接复制到activity的onCreate就可以了，把音乐放到assets文件夹，再把引号里的名字换成你的音乐文件的名字。运行。
       // MediaPlayer mpMediaPlayer = new MediaPlayer();
        AssetManager am = getAssets();
        try {
            mpMediaPlayer.setDataSource(am.openFd("haha.mp3").getFileDescriptor());
            mpMediaPlayer.prepare();
            mpMediaPlayer.start();
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}