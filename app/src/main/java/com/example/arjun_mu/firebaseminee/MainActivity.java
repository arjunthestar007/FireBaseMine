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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
