package com.unlimitedappworks.grabadora;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Grabar grabar;
    Reproducir reproducir;
    TextView tvEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEstado=(TextView)findViewById(R.id.tv_estado);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                if (reproducir != null) {
                    reproducir.stop();
                }
                if (grabar != null) {
                    grabar.detener();
                    grabar = null;
                }
                reproducir = new Reproducir();
                reproducir.play();
                tvEstado.setText("Reproduciendo");
                break;
            case R.id.btn_grabar:
                if (reproducir != null) {
                    reproducir.stop();
                    reproducir = null;
                }
                if (grabar != null) {
                    grabar.detener();
                }
                grabar = new Grabar();
                grabar.iniciarGrabacion();
                tvEstado.setText("Grabando");
                break;
            case R.id.btn_detener:
                if (reproducir != null) {
                    reproducir.stop();
                    reproducir = null;
                }
                if (grabar != null) {
                    grabar.detener();
                    grabar = null;
                }
                tvEstado.setText("En espera");
                break;
        }
    }

    public class Reproducir {
        private MediaPlayer mp;

        public Reproducir() {
            mp = new MediaPlayer();
            try {
                mp.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/rec.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void play() {
            try {
                mp.prepare();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            mp.stop();
        }
    }

    public class Grabar {
        private MediaRecorder mr;

        public Grabar() {
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mr.setAudioEncoder(MediaRecorder.OutputFormat.DEFAULT);
            mr.setOutputFile(Environment.getExternalStorageDirectory().getPath() + "/rec.wav");
        }

        public Grabar iniciarGrabacion() {
            try {
                mr.prepare();
                mr.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public void detener() {
            mr.stop();
        }
    }
}
