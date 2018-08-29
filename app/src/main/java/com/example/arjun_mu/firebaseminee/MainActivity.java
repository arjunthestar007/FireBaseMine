package com.example.arjun_mu.firebaseminee;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    public static final String QUOTE = "quote";
    public static final String AUTHOR = "author";
    EditText etauthor;
    EditText etquote;
    DocumentReference mdocref = FirebaseFirestore.getInstance().document("sampledata/inspiration");
    private static final String TAG = "MainActivity";
    TextView tvfetch;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etauthor = findViewById(R.id.editTextauthor);
        etquote = findViewById(R.id.editTextquote);
        tvfetch=findViewById(R.id.fetch);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mdocref.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    String quote=documentSnapshot.getString(QUOTE);
                    String author=documentSnapshot.getString(AUTHOR);
                    tvfetch.setText(quote+"---"+author);
                }
                else if(e!=null){
                    Log.w(TAG,"got an exception");
                }
            }
        });
    }

    public void savequote(View view) {
        String authortext = etauthor.getText().toString();
        String quotetext = etquote.getText().toString();

        if (authortext.isEmpty() || quotetext.isEmpty()) {
            return;
        }

        Map<String, Object> datatosave = new HashMap<>();
        datatosave.put(QUOTE, quotetext);
        datatosave.put(AUTHOR, authortext);

        mdocref.set(datatosave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: ");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }

    public void fetchquote(View view) {
        mdocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String quote=documentSnapshot.getString(QUOTE);
                    String author=documentSnapshot.getString(AUTHOR);
                    tvfetch.setText(quote+"---"+author);
                }

            }
        });
    }
}
