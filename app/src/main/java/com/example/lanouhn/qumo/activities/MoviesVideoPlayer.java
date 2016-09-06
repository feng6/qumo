package com.example.lanouhn.qumo.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.lanouhn.qumo.R;

/**
 * Created by lanouhn on 2016/8/20.
 */
public class MoviesVideoPlayer  extends Activity {


    private VideoView videoView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_item);

        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        Uri uri = Uri.parse("url");
        videoView = (VideoView) this.findViewById(R.id.video);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}