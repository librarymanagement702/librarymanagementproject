package com.vjay.libararymanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vjay.libararymanagement.model.AppUser;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String LOGIN_USER_ARG = "LOGIN_USER";
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private EditText username;
    private EditText password;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String LOGIN_USER = "currentuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = findViewById(R.id.login_uname);
        password = findViewById(R.id.login_password);

    }

    public void loginToApp(View v) {
        Log.d(TAG, "Login clicked>>>>>>>>>>>>>>>>>");
        String userNameTxt = username.getText().toString();
        String passwordTxt = password.getText().toString();
        Log.d(TAG, "Login clicked>>>>>>>>>>>>>>>>>" + userNameTxt + "-" + passwordTxt);
        if (userNameTxt == null || userNameTxt.isEmpty() || passwordTxt.isEmpty() || passwordTxt == null) {
            Toast.makeText(this, "Please provide both username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("users").document(userNameTxt).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (!documentSnapshot.exists()) {
                        Toast.makeText(MainActivity.this, "User not found !", Toast.LENGTH_SHORT).show();
                    } else {
                        AppUser user = documentSnapshot.toObject(AppUser.class);
                        if (user.getPassword().equals(passwordTxt)) {
                            clearInputField();
                            Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                            intent.putExtra(LOGIN_USER_ARG, user);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(LOGIN_USER, user.getName());
                            editor.commit();
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                            alertDialog.setMessage("Invalid password");
                            alertDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                            alertDialog.create().show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error adding document..." + e.getMessage());
                    Toast.makeText(MainActivity.this, "User fetch failed !", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearInputField() {
        username.getText().clear();
        password.getText().clear();
    }

    public void gotoRegister(View v) {

        Intent intent = new Intent(this, ResgiterActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }


    public void gotoScanner(View v) {
        launchBarCodeActivity();
    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "error in  scanning", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            String qrReult = barcode.rawValue;
            if (qrReult != null) {
                String[] resultArr = qrReult.split(",");
                if (resultArr[0].contains("Register No") && resultArr[1].contains("Name :")) {
                    String userName = resultArr[1].split(":")[1];
                    String registerNo = resultArr[0].split(":")[1].trim();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle("Authenticate");
                    builder.setPositiveButton("Cancel", (dialog, which) -> dialog.dismiss());
                    builder.setNeutralButton("Login", (dialog, which) -> {
                        proceedLogin(userName, registerNo);
                    });

                    builder.setMessage("Welcome " + userName + "!\nRegister No:" + registerNo + ".\nWould you like to login ?");
                    android.app.AlertDialog alert1 = builder.create();
                    alert1.show();
                } else {
                    showInValidQRMessage();
                }
            }

        }
    }

    private void showInValidQRMessage() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Invalid QRCode");
        builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());

        builder.setMessage("The Qr Code you have used is inavlid.\n Please try again with valid Qr Code.");
        android.app.AlertDialog alert1 = builder.create();
        alert1.show();
    }

    private void proceedLogin(String userName, String registerNo) {
        db.collection("users").whereEqualTo("registerNumber", registerNo).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().size() == 0) {
                    showRegisterMessage(userName);
                    return;
                }
                for (QueryDocumentSnapshot document : task.getResult()) {
                    AppUser user = document.toObject(AppUser.class);
                    Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                    intent.putExtra(LOGIN_USER_ARG, user);
                    startActivity(intent);
                }
            } else {
                showRegisterMessage(userName);
            }
        }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failure" + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void showRegisterMessage(String username) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Unable to process QRCode");
        builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());

        builder.setMessage("Hey " + username + "!\nLooks like you have not registered yet.\n Please register first and try login.");
        android.app.AlertDialog alert1 = builder.create();
        alert1.show();
    }

}
