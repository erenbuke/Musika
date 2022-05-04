package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    ArrayList<Music> musics = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        if(!checkPermission()){
            getPermission();
        }

        RecyclerView recycler = findViewById(R.id.recycler);

        findMusic();

        Log.d("MUSICS", "Starting to print audio files...");

        for(Music newmusic: musics){
            Log.d("MUSICS", newmusic.name);
        }

        Log.d("MUSICS", "End of audio files");

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new ListAdapter(musics, getApplicationContext()));

    }

    public ArrayList<Music> findMusic() {

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC;

        Cursor c = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, MediaStore.Audio.Media.TITLE);

        while (c.moveToNext()) {
            Music newmusic = new Music();

            newmusic.duration = c.getString(3);
            newmusic.album = c.getString(1);
            newmusic.name = c.getString(0);
            newmusic.path = c.getString(4);
            newmusic.artist = c.getString(2);

            if(new File(newmusic.path).exists()){
                musics.add(newmusic);
            }
        }

        return musics;
    }

    boolean checkPermission(){
        int permission = ContextCompat.checkSelfPermission(MainPage.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permission == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    void getPermission(){
        ActivityCompat.requestPermissions(MainPage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }
}