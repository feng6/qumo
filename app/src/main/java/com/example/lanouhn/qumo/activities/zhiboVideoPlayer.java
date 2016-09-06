package com.example.lanouhn.qumo.activities;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.*;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Toast;


import com.example.lanouhn.qumo.R;

import com.example.lanouhn.qumo.constans.playVideo.PlayVideo;
import com.example.lanouhn.qumo.constans.playVideo.urls;
import com.example.lanouhn.qumo.im.LiveKit;
import com.example.lanouhn.qumo.im.animation.HeartLayout;
import com.example.lanouhn.qumo.im.fragments.BottomPanelFragment;
import com.example.lanouhn.qumo.im.message.GiftMessage;
import com.example.lanouhn.qumo.im.widget.ChatListAdapter;
import com.example.lanouhn.qumo.im.widget.ChatListView;
import com.example.lanouhn.qumo.im.widget.InputPanel;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.example.lanouhn.qumo.utils.PlayVideo_JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by lanouhn on 2016/8/27.
 */
public class zhiboVideoPlayer extends AppCompatActivity implements View.OnClickListener {

    private ChatListView chatListView;    //聊天窗口
    private BottomPanelFragment bottomPanel;    //输入窗口
    private ChatListAdapter chatListAdapter;
    //  private String roomId;

    private ViewGroup background;//聊天背景
    private ImageView btnGift;//送礼物
    private ImageView btnHeart;//点赞♥
    private Random random = new Random();//点赞轨迹随机
    private HeartLayout heartLayout;

    private int id;    //接收到的用于拼接视频接口的id
    private String securityUrl;    //视频播放网址
    private String ext;    //视频格式

    private PlayVideo playVideo;    //视频播放的实体类

    private List<com.example.lanouhn.qumo.constans.playVideo.playLines> playLines = new ArrayList<>();
    private List<urls> urlses = new ArrayList<>();


    private VideoView mVideoView;
    private MediaController mMediaController;
    private String path1;//视频主播网络地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        Vitamio.isInitialized(this);//启动vitamio
        setContentView(R.layout.zhiboplay);
        //找到聊天布局控件
        chatListView = (ChatListView) findViewById(R.id.chat_listview);
        bottomPanel = (BottomPanelFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_bar);
        heartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        btnGift = (ImageView) bottomPanel.getView().findViewById(R.id.btn_gift);
        btnHeart = (ImageView) bottomPanel.getView().findViewById(R.id.btn_heart);
        background = (ViewGroup)findViewById(R.id.background);
        LiveKit.addEventHandler(handler);


        mVideoView = (VideoView) findViewById(R.id.surface_view);
        Intent intent = getIntent();
            id = intent.getIntExtra("id",0);

        path1 = "http://livestream.plu.cn/live/GetLivePlayUrl?roomId=" + id + "&appId=4001&version=3.6.2&device=4";
        initData();



        chatListAdapter = new ChatListAdapter();
        chatListView.setAdapter(chatListAdapter);
        //聊天布局控件监听
        background.setOnClickListener(this);
        btnGift.setOnClickListener(this);
        btnHeart.setOnClickListener(this);
        bottomPanel.setInputPanelListener(new InputPanel.InputPanelListener() {
            @Override
            public void onSendClick(String text) {
                final TextMessage content = TextMessage.obtain(text);
                LiveKit.sendMessage(content);
            }
        });

        joinChatRoom(String.valueOf(id));
    }


    private void joinChatRoom(final String roomId) {

//设置用户
        LiveKit.setCurrentUser(new UserInfo("风铃子", "123456", Uri.parse("http://7xs9j5.com1.z0.glb.clouddn.com/liveapp/zhenhuan.jpg")));
//加入房间
        LiveKit.joinChatRoom(roomId, 2, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                final InformationNotificationMessage content = InformationNotificationMessage.obtain("来啦");
                LiveKit.sendMessage(content);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Toast.makeText(zhiboVideoPlayer.this, "聊天室加入失败! errorCode = " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                // String result = path1;
                String result = HttpUtils.doGet(path1);

                if (null != result && result.length() > 0) {
                    playVideo = PlayVideo_JsonUtil.getData(result);
                    if (!playVideo.equals("")) {
                        handler.sendEmptyMessage(1);

                        Log.e("aaaaa", "njds1nvsdjknv");
                    } else {
                        handler.sendEmptyMessage(0);
                    }

                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    //判断网络是否连接
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //赋值，循环遍历数据
                    playLines = playVideo.getPlayLines();

                    for (int x = 0; x < playLines.size(); x++) {
                        urlses = playLines.get(x).getUrls();
                        for (int y = 0; y < urlses.size(); y++) {
                            ext = urlses.get(y).getExt();
                            //获取格式为m3u8的视频的接口
                            if (ext.equals("m3u8")) {

                                Log.e("aaaaa", "y" + "========" + y);

                                securityUrl = urlses.get(y).getSecurityUrl();
                                mVideoView.setVideoPath(securityUrl);
                                Log.e("aaaaa", "securityUrl" + "==========" + securityUrl);

                                mMediaController = new MediaController(zhiboVideoPlayer.this);

                                mMediaController.show(5000);//控制器显示5s后自动隐藏
//                                mVideoView.setMediaController(mMediaController);
                                // mVideoView.setMediaController(mMediaController);    //绑定自定义的控制器
                                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
                                        mediaPlayer.setPlaybackSpeed(1.0f);
                                    }
                                });
                                mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);    //设置播放画质 高画质
                                mVideoView.requestFocus();    //取得焦点
                            }
                        }
                    }

                    break;
                case 0:
                    Toast.makeText(zhiboVideoPlayer.this, R.string.fail, Toast.LENGTH_SHORT).show();
                    break;
                case LiveKit.MESSAGE_ARRIVED: {
                    MessageContent content = (MessageContent) msg.obj;
                    chatListAdapter.addMessage(content);
                }
                break;
                case LiveKit.MESSAGE_SENT: {
                    MessageContent content = (MessageContent) msg.obj;
                    chatListAdapter.addMessage(content);
                }
                break;
                case LiveKit.MESSAGE_SEND_ERROR: {
                    break;
                }
            }
            chatListAdapter.notifyDataSetChanged();
        }
    };
    //聊天布局控件监听
    @Override
    public void onClick(View v) {
        if (v.equals(background)) {
            bottomPanel.onBackAction();
        } else if (v.equals(btnGift)) {
            GiftMessage msg = new GiftMessage("2", "送您一个礼物");
            LiveKit.sendMessage(msg);
        } else if (v.equals(btnHeart)) {
            heartLayout.post(new Runnable() {
                @Override
                public void run() {
                    int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    heartLayout.addHeart(rgb);
                }
            });
            GiftMessage msg = new GiftMessage("1", "为您点赞");
            LiveKit.sendMessage(msg);
        }

    }
}
