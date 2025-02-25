package com.example.helloconstraint;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int trackCount=0;
    private TextView showCount;
    private Button btnZero;
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
        btnZero = (Button) findViewById(R.id.btnzero);

    }

    public void showToast(View view) {
        String toast_message = "";
        Toast.makeText(this, showCount.getText(), Toast.LENGTH_SHORT).show();

    }

    public void countUp(View view) {

        if(trackCount==0){
            int Pink = ContextCompat.getColor(this, R.color.pink);
            btnZero.setBackgroundColor(Pink);

        }
        trackCount++;
        showCount.setText(Integer.toString(trackCount));

    }

    public void EventZero(View view) {
        trackCount = 0;
        showCount.setText(Integer.toString(trackCount));
        int Gray = ContextCompat.getColor(this, R.color.Gray);
        btnZero.setBackgroundColor(Gray);

    }
}