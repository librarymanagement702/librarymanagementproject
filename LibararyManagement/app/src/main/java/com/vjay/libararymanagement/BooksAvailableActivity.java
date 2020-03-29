package com.vjay.libararymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vjay.libararymanagement.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksAvailableActivity extends AppCompatActivity {
    private static final String TAG = "BooksAvailableActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_available);
        lista = findViewById(R.id.booksList);

        db.collection("books").get().addOnCompleteListener(task -> {
            List<Book> bookList = new ArrayList<>();
            if (task.isComplete()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Book user = document.toObject(Book.class);
                    bookList.add(user);
                }
                BookAdapter mProductAdapter = new BookAdapter(BooksAvailableActivity.this, bookList);
                mProductAdapter.notifyDataSetChanged();
                lista.setAdapter(mProductAdapter);
                lista.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(BooksAvailableActivity.this, BookDetailActivity.class);
                    intent.putExtra("BOOK_SELECTED", bookList.get(position));
                    startActivity(intent);
                });

            }

        });
    }

}
