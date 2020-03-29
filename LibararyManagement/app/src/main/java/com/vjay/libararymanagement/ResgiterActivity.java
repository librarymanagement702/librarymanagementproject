package com.vjay.libararymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        cpassword = findViewById(R.id.cpassword);
        npassword = findViewById(R.id.npassword);
    }

    public void register(View v) {
        Log.d(TAG, "Login clicked>>>>>>>>>>>>>>>>>");
        String userNameTxt = username.getText().toString();
        String nameTxt = name.getText().toString();
        String registerNoTxt = registerNo.getText().toString();
        String password = cpassword.getText().toString();
        String newpassword = npassword.getText().toString();
        AppUser user = new AppUser(nameTxt, userNameTxt, registerNoTxt, password, UserRole.STUDENT);
        Log.d(TAG, "item to save>>>>>>>>>>>>>>>>>" + userNameTxt);

        if (userNameTxt.isEmpty() || nameTxt.isEmpty() || registerNoTxt.isEmpty() || password.isEmpty() || newpassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all the User details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(newpassword)) {
            Toast.makeText(this, "New password and confirm password mst be same.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            db.collection("users").document(userNameTxt).set(user)
                    .addOnSuccessListener(avoid -> {
                        Log.d(TAG, "DocumentSnapshot added with ID: ");
                        clearInputField();
                        Toast.makeText(ResgiterActivity.this, "User added", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.d(TAG, "Error adding document");
                        Toast.makeText(ResgiterActivity.this, "Error adding User !", Toast.LENGTH_SHORT).show();
                    });
        } catch (Error e) {
            Log.d(TAG, e.toString());
        }
    }

    public void gotoLogin(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void clearInputField() {
        username.getText().clear();
        name.getText().clear();
        registerNo.getText().clear();
        cpassword.getText().clear();
        npassword.getText().clear();
    }
}
