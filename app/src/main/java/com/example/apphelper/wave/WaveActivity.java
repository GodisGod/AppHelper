package com.example.apphelper.wave;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.apphelper.R;
import com.example.apphelper.wave.recordwave.VoiceLineView;

import java.io.File;
import java.io.IOException;

public class WaveActivity extends AppCompatActivity implements Runnable {

    private SongWave songWave;
    private SongWave2 songWave2;
    private FingerWaveView waveView;
    private Button btnStart;
    private Button btnStop;

    private MediaRecorder mMediaRecorder;
    private boolean isAlive = true;
    private VoiceLineView voiceLineView;
    private VoiceLineView voiceLineView2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mMediaRecorder == null) return;
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / 100;
            double db = 0;// 分贝
            //默认的最大音量是100,可以修改，但其实默认的，在测试过程中就有不错的表现
            //你可以传自定义的数字进去，但需要在一定的范围内，比如0-200，就需要在xml文件中配置maxVolume
            //同时，也可以配置灵敏度sensibility
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            //只要有一个线程，不断调用这个方法，就可以使波形变化
            //主要，这个方法必须在ui线程中调用
            Log.i("LHD", "handleMessage = 音量 = " + db);
            voiceLineView.setVolume((int) (db));
            voiceLineView2.setVolume((int) (db));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        songWave = findViewById(R.id.song_wave);
        songWave2 = findViewById(R.id.song_wave2);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_reset);
        waveView = findViewById(R.id.wave_view);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!songWave2.isRuning()) {
                    songWave2.start();
                }
                if (!songWave.isRuning()) {
                    songWave.start();
                }
                waveView.start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songWave.reset();
                songWave2.reset();
                waveView.stop();
            }
        });

        voiceLineView = (VoiceLineView) findViewById(R.id.voicLine);
        voiceLineView2 = (VoiceLineView) findViewById(R.id.voicLine2);
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "hello.log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMediaRecorder.setOutputFile(file.getAbsolutePath());
        mMediaRecorder.setMaxDuration(1000 * 60 * 10);
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaRecorder.start();

        Thread thread = new Thread(this);
        thread.start();

    }


    @Override
    protected void onDestroy() {
        isAlive = false;
        mMediaRecorder.release();
        mMediaRecorder = null;
        super.onDestroy();
    }

    @Override
    public void run() {
        while (isAlive) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
