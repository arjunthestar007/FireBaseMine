package com.example.arjun_mu.firebaseminee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        TextView textView=findViewById(R.id.textView);
        Intent intent=getIntent();
        String s=intent.getStringExtra("msg");
        textView.setText(s);
    }
}
