package com.vjay.libararymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vjay.libararymanagement.model.AppUser;
import com.vjay.libararymanagement.model.UserRole;

public class ResgiterActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText username;
    private EditText name;
    private EditText registerNo;
    private EditText cpassword;
    private EditText npassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgiter);

        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        registerNo = findViewById(R.id.registerNo);
        cpassword = findViewById(R.id.npassword);
        npassword = findViewById(R.id.cpassword);
    }

    public void register(View v){
        Log.d(TAG,"Login clicked>>>>>>>>>>>>>>>>>");
        String userNameTxt = username.getText().toString();
        String nameTxt = name.getText().toString();
        String registerNoTxt = registerNo.getText().toString();
        String password = cpassword.getText().toString();
        AppUser user = new AppUser(nameTxt, userNameTxt,registerNoTxt,password, UserRole.STUDENT);
        Log.d(TAG,"item to save>>>>>>>>>>>>>>>>>"+userNameTxt);
        try{
            db.collection("users").document(userNameTxt).set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Log.d(TAG, "DocumentSnapshot added with ID: ");
                            Toast.makeText(ResgiterActivity.this, "User added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Error adding document");
                            Toast.makeText(ResgiterActivity.this, "Error adding User !", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Error e) {
            Log.d(TAG, e.toString());
        }
    }

    public void gotoLogin(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
