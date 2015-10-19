package kr.dont.color_detect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button processing1Button = (Button) findViewById(R.id.processingGRAYButton);
        processing1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProcessingGRAYActivity.class);
                startActivity(intent);
            }
        });
        Button processing5Button = (Button) findViewById(R.id.processingYCrCbButton);
        processing5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProcessingYCrCbActivity.class);
                startActivity(intent);
            }
        });
        Button processingHSVButton = (Button) findViewById(R.id.processingHSVButton);
        processingHSVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProcessingHSVActivity.class);
                startActivity(intent);
            }
        });
    }
}
