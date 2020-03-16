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
        db.collection("users").document(userNameTxt).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                String dialogMSfg = "";
                                //Toast.makeText(MainActivity.this, "User found !", Toast.LENGTH_SHORT).show();
                                AppUser user  = documentSnapshot.toObject(AppUser.class);
                                if(user.getPassword().equals(passwordTxt)){
                                    dialogMSfg = "User signed in succesfully!";
                                }else{
                                    dialogMSfg = "Invalid password";
                                }
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                                alertDialog.setMessage(dialogMSfg);
                                alertDialog.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //dismiss the dialog
                                    }
                                });
                                alertDialog.create().show();


                            }else{
                                Toast.makeText(MainActivity.this, "User not found !", Toast.LENGTH_SHORT).show();
                            }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error adding document");
                        Toast.makeText(MainActivity.this, "User fetch failed !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void gotoRegister(View v) {

        Intent intent = new Intent(this, ResgiterActivity.class);
        startActivity(intent);

    }

}
