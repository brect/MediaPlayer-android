package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;


    private Button btnPlay;
    private Button btnPause;
    private Button btnStop;

    private SeekBar ajustarVolume;
    private AudioManager configuraAudioAparelho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);


        btnPlay = findViewById(R.id.button_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executarSom(btnPlay);
            }
        });

        btnPause = findViewById(R.id.button_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausarSom(btnPause);
            }
        });

        btnStop = findViewById(R.id.button_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararSom(btnStop);
            }
        });


        ajustarVolume = findViewById(R.id.seekBarVolume);
        configuraVolume(ajustarVolume);


    }

    public void executarSom(View view){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void pausarSom(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void pararSom(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void configuraVolume(View view){

        //configura o audio maneger
        configuraAudioAparelho = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //recupera os valores de volume maximo e o volume atual
        int volumeMaximo = configuraAudioAparelho.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = configuraAudioAparelho.getStreamVolume(AudioManager.STREAM_MUSIC);

        //configura os valores maximos para o seekbar
        ajustarVolume.setMax(volumeMaximo);

        //configura o profresso atual do seekBar
        ajustarVolume.setProgress(volumeAtual);

        ajustarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int valorVolume, boolean fromUser) {
                configuraAudioAparelho.setStreamVolume(AudioManager.STREAM_MUSIC, valorVolume, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
