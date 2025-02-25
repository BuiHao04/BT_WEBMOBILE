package com.example.hellotoastchallenge;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int trackCount=0;
    private TextView showCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // anh xa
        showCount = (TextView) findViewById(R.id.show_count);

    }

    public void showToast(View view) {
        String toast_message = "";
        Toast.makeText(this, showCount.getText(), Toast.LENGTH_SHORT).show();

    }

    public void countUp(View view) {
        trackCount++;
        showCount.setText(Integer.toString(trackCount));

    }
}