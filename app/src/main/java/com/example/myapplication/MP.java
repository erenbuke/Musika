package com.example.myapplication;

import android.media.MediaPlayer;

public class MP {

    static MediaPlayer instance;

    public static MediaPlayer getInstance(){

        if(instance == null){
            instance = new MediaPlayer();
        }

        return instance;
    }

    public static int index = -1;
}
