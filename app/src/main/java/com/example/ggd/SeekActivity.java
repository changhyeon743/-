package com.example.ggd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


public class SeekActivity extends AppCompatActivity {

    RateManager rateManager;
    SeekBar seekBar;
    TextView textView;
    TextView textView2;
    String station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);

        seekBar = findViewById(R.id.seekBar1);
        textView = findViewById(R.id.text);
        textView2 = findViewById(R.id.text2);

        station = getIntent().getStringExtra("station");
        rateManager = new RateManager();
        rateManager.setStation(station);
        if(rateManager.getRate() != -1){
            textView2.setText("저번에 " + rateManager.getRate()+"점을 주셨어요.");
        } else{
            textView2.setText("아직 점수가 없습니다!");
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textView.setText(progress+"점을 부여하시겠습니까?");
            }
        });

    }

    public void OnConfirmClicked(View v){
        rateManager.setRate(seekBar.getProgress());
        Toast.makeText(getApplicationContext(),"성공적으로 반영되었습니다!", Toast.LENGTH_SHORT).show();
        textView2.setText("저번에 " + rateManager.getRate()+"점을 주셨어요.");
    }


    public class RateManager {

        String station;

        public void setStation(String name){ //역 이름 설정 (별점 설정 or 받아오기 전)
            station = name;
        }

        public int getRate(){ //점수를 받아오는데 값이 만약 -1이면 아직 설정하지 않은 상태이므로 표시하지 않음
            int rate;
            SharedPreferences sf = getSharedPreferences("rate", MODE_PRIVATE);
            rate = sf.getInt(station, -1);
            return rate;
        }

        public void setRate(int value){
            SharedPreferences sf = getSharedPreferences("rate", MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.putInt(station, value);
            editor.commit();
        }

    }

}
