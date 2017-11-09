package io.github.putme2yourheart.numberprogressbar;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private NumberProgressBar numberpb;
    private NumberProgressBar numberbp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberpb = (NumberProgressBar) findViewById(R.id.numberpb);
        numberbp1 = (NumberProgressBar) findViewById(R.id.numberpb1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                numberpb.setMaxProgress(100);
                numberpb.setProgressWithAnimation(100, 5000);

                numberbp1.setMaxProgress(100);
                numberbp1.setProgressWithAnimation(100, 5000);
            }
        }, 1000);
    }
}
