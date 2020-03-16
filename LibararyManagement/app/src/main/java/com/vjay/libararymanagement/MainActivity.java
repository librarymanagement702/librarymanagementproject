package com.vjay.libararymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vjay.libararymanagement.model.AppUser;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String LOGIN_USER_ARG = "LOGIN_USER";
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.login_uname);
        password = findViewById(R.id.login_password);

    }

    public void loginToApp(View v) {
        Log.d(TAG, "Login clicked>>>>>>>>>>>>>>>>>");
        String userNameTxt = username.getText().toString();
        String passwordTxt = password.getText().toString();
        Log.d(TAG, "Login clicked>>>>>>>>>>>>>>>>>"+userNameTxt+ "-"+passwordTxt);
        if(userNameTxt == null || userNameTxt.isEmpty() || passwordTxt.isEmpty() ||  passwordTxt == null){
            Toast.makeText(this, "Please provide both username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("users").document(userNameTxt).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (!documentSnapshot.exists()) {
                        Toast.makeText(MainActivity.this, "User not found !", Toast.LENGTH_SHORT).show();
                    } else {
                        AppUser user = documentSnapshot.toObject(AppUser.class);
                        if(user.getPassword().equals(passwordTxt)){
                            clearInputField();
                            Intent intent = new Intent(MainActivity.this, DashBoard.class);
                            intent.putExtra(LOGIN_USER_ARG ,user);
                            startActivity(intent);
                        }else{
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                            alertDialog.setMessage("Invalid password");
                            alertDialog.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.create().show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error adding document..."+e.getMessage());
                    Toast.makeText(MainActivity.this, "User fetch failed !", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearInputField(){
        username.getText().clear();
        password.getText().clear();
    }
    public void gotoRegister(View v) {

        Intent intent = new Intent(this, ResgiterActivity.class);
        startActivity(intent);

    }


    public void gotoScanner(View v) {
        Intent intent = new Intent(this, QrCodeScannerActivity.class);
        startActivity(intent);
    }

}
