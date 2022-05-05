package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class MusicPage extends AppCompatActivity {

    TextView musicTitle, currentTime, totalTime, artistName, albumName;

    ImageView prev, next, pauseResume, stop, musicIcon;

    SeekBar bar;

    ArrayList<Music> musics;
    MediaPlayer player = MP.getInstance();
    MediaMetadataRetriever metaRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);

        musicTitle = findViewById(R.id.music_title);
        artistName = findViewById(R.id.artist_name);
        albumName = findViewById(R.id.album_name);
        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);
        musicIcon = findViewById(R.id.music_icon);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        pauseResume = findViewById(R.id.pause_resume);
        stop = findViewById(R.id.stop);
        bar = findViewById(R.id.progress);

        musics = (ArrayList<Music>) getIntent().getSerializableExtra("SONGS");

        play();

        pauseResume.setOnClickListener(v -> pause_resume());
        next.setOnClickListener(v -> next_song());
        prev.setOnClickListener(v -> prev_song());
        stop.setOnClickListener(v -> stopsong());

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(player != null && b){
                    player.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(player != null){
                    bar.setProgress(player.getCurrentPosition());
                    currentTime.setText(formateMilliSeccond(player.getCurrentPosition()));

                    if(currentTime.getText().equals(totalTime.getText())){
                        next_song();
                    }
                }

                new Handler().postDelayed(this, 100);
            }

        });

    }

    private void play(){

        Music music = musics.get(MP.index);

        MediaMetadataRetriever dataRetriever = new MediaMetadataRetriever();
        dataRetriever.setDataSource(music.path);
        Bitmap image;

        try {
            byte[] art = dataRetriever.getEmbeddedPicture();
            image = BitmapFactory.decodeByteArray(art, 0, art.length);
            musicIcon.setImageBitmap(image);
        } catch (Exception e){
            musicIcon.setImageResource(R.drawable.musicicon);
        }

        musicTitle.setText(music.name);

        if(music.artist != null){
            artistName.setText(music.artist);
        }
        else{
            artistName.setText("Unknown Artist");
        }

        if (music.album != null){
            albumName.setText(music.album);
        }
        else{
            albumName.setText("Unknown Album");
        }

        currentTime.setText("00.00");
        totalTime.setText(formateMilliSeccond(Long.parseLong(music.duration)));
        bar.setProgress(0);
        int a = Integer.parseInt(music.duration);
        Log.d("TAG", a + " " + music.duration);
        bar.setMax(a);

        player.reset();

        try {
            player.setDataSource(music.path);
            player.prepare();
            player.start();


        }catch (Exception e){
            e.getMessage();
        }
    }

    private void next_song(){

        if(MP.index == musics.size() - 1){
            MP.index = 0;
        }
        else {
            MP.index++;
        }

        play();

    }

    private void prev_song(){

        if(bar.getProgress() < 5000){

            if(MP.index == 0){
                MP.index = musics.size() - 1;
            }
            else{
                MP.index--;
            }

            play();

        }
        else{
            player.seekTo(0);
        }

    }

    private void pause_resume(){

        if(player.isPlaying()){
            player.pause();
            pauseResume.setImageResource(R.drawable.play);
        }
        else{
            player.start();
            pauseResume.setImageResource(R.drawable.pause);
        }

    }

    private void stopsong(){
        player.reset();
        this.finish();
    }

    public static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        String minutesString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ".";
        }

        if(minutes < 10){
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutesString + "." + secondsString;

        return finalTimerString;
    }
}