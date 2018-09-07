package com.example.arjun_mu.firebaseminee;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mcondtionRef = databaseReference.child("condition");
    TextView condtionTextView;
    Button sunnybtn;
    Button foggybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        condtionTextView = findViewById(R.id.condtition);
        sunnybtn = findViewById(R.id.sunny);
        foggybtn = findViewById(R.id.foggy);

        sunnybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcondtionRef.setValue("sunny");  // set the value to mcondtionRef instead of local
            }
        });

        foggybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcondtionRef.setValue("foggy");

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        // calls when there is a data change
        mcondtionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                condtionTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
