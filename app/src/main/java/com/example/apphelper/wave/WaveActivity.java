package com.example.apphelper.wave;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apphelper.R;

public class WaveActivity extends AppCompatActivity {

    private SongWave songWave;
    private SongWave2 songWave2;
    private FingerWaveView waveView;
    private Button btnStart;
    private Button btnStop;

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

    }
}
