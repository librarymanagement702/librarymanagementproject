package com.vjay.libararymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vjay.libararymanagement.model.AppUser;
import com.vjay.libararymanagement.model.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vjay.libararymanagement.MainActivity.LOGIN_USER_ARG;

public class DashBoardActivity extends AppCompatActivity {

    TextView title;
    AppUser loginUser;
    static Map<String, Class> activityLookup;

    static {
        activityLookup = new HashMap<>();
        activityLookup.put("Books available", BooksAvailableActivity.class);
        activityLookup.put("Update book return", UpdateBookReturnActivity.class);
        activityLookup.put("Add new book", AddBookActivity.class);
        activityLookup.put("Get new books", GetNewBookActivity.class);
        activityLookup.put("Books availed", BookAvailedByUserActivity.class);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        loginUser = (AppUser) getIntent().getSerializableExtra(LOGIN_USER_ARG);
        final ListView list = findViewById(R.id.listView1);
        List<String> menus = (loginUser.getRole().equals(UserRole.ADMIN)) ? getAdminMenus() : getStudentMenus();
        ArrayAdapter<String> adp = new ArrayAdapter
                (getBaseContext(), R.layout.list, menus);
        list.setAdapter(adp);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(DashBoardActivity.this, activityLookup.get(menus.get(position)));
            intent.putExtra(LOGIN_USER_ARG, loginUser);
            startActivity(intent);
        });
    }

    public List<String> getAdminMenus() {
        List<String> list = new ArrayList();
        list.add("Books available");
        list.add("Update book return");
        list.add("Add new book");
        return list;
    }

    public List<String> getStudentMenus() {
        List<String> list = new ArrayList();
        list.add("Books available");
        list.add("Get new books");
        list.add("Books availed");
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.layout.list, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashBoardActivity.this);
        alertDialog.setMessage("Are you sure you want to exit ?");
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            moveTaskToBack(true);
            Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
            startActivity(intent);
            moveTaskToBack(true);

        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialog.create().show();
    }
}
