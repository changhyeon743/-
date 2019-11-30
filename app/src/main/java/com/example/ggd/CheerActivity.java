package com.example.ggd;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class CheerActivity extends AppCompatActivity {
    ImageView imageView;
    ArrayList<Integer> medias = new ArrayList<>();
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheer);

        imageView = findViewById(R.id.cheerup);

        medias.add(R.raw.untitled2);
        medias.add(R.raw.untitled3);
        medias.add(R.raw.untitled4);
        medias.add(R.raw.untitled5);

        mp = MediaPlayer.create(this, R.raw.untitled2);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaySound();
                //Toast.makeText(CheerActivity.this, "힘내세요!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void PlaySound() {
        Random random = new Random();
        Integer randomElement = medias.get(random.nextInt(medias.size()));
        mp = MediaPlayer.create(this,randomElement);
        mp.start();
    }

}
