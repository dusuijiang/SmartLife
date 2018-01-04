package cn.com.example.smartlife.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import cn.com.example.smartlife.R;

public class VideoViewActivity extends AppCompatActivity {

    String source = "https://qiubai-video.qiushibaike.com/A14EXG7JQ53PYURP.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(source));
        videoView.start();

    }

    private static final String TAG = "VideoViewActivity";
}
