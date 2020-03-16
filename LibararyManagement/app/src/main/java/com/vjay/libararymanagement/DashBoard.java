package com.vjay.libararymanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vjay.libararymanagement.model.AppUser;
import com.vjay.libararymanagement.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class DashBoard extends AppCompatActivity {

    TextView title;
    AppUser loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        loginUser = (AppUser) getIntent().getSerializableExtra(MainActivity.LOGIN_USER_ARG);
        final ListView list= findViewById(R.id.listView1);
        List<String> menus = (loginUser.getRole().equals(UserRole.ADMIN)) ? getAdminMenus() : getStudentMenus();
        ArrayAdapter<String> adp=new ArrayAdapter
                (getBaseContext(),R.layout.list,menus);
        list.setAdapter(adp);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DashBoard.this, menus.get(position)+" is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<String> getAdminMenus(){
        List<String> list = new ArrayList();
        list.add("Books available");
        list.add("Update book return");
        list.add("Add new book");
        return list;
    }

    public List<String> getStudentMenus(){
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
       // super.onBackPressed();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashBoard.this);
        alertDialog.setMessage("Are you sure you want to exit ?");
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {dialog.dismiss();  super.onBackPressed();});
        alertDialog.setNegativeButton("No",  (dialog, which) -> dialog.dismiss());
        alertDialog.create().show();
       // moveTaskToBack(true);
    }
}
