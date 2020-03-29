package com.vjay.libararymanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.vjay.libararymanagement.model.Book;

public class AddBookActivity extends AppCompatActivity {
    EditText bookTitleTV;
    EditText authorNameTv;
    EditText descriptionTv;
    EditText bookCountTv;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ADD BOOK ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        bookTitleTV = findViewById(R.id.abBookTitle);
        authorNameTv = findViewById(R.id.abAuthorName);
        descriptionTv = findViewById(R.id.abBookDesc);
        bookCountTv = findViewById(R.id.abBookCount);

    }

    public void addNewBook(View view) {

        String bookName = bookTitleTV.getText().toString();
        String authorName = authorNameTv.getText().toString();
        String description = descriptionTv.getText().toString();
        String bookCount = bookCountTv.getText().toString();
        if (bookName.isEmpty() || authorName.isEmpty() || bookCount.isEmpty()) {
            Toast.makeText(this, "Please fill in all the Book details", Toast.LENGTH_SHORT).show();
            return;
        }
        Book newBook = new Book();
        newBook.setBookName(bookName);
        newBook.setAuthorName(authorName);
        newBook.setBookCount(bookCount);
        newBook.setBooksAvailableForStudent(Integer.parseInt(bookCount));
        newBook.setDescription(description);
        Log.d(TAG, "BOOK to save CREATED>>>>>>>>>>>>>>>>>");
        try {
            db.collection("books").document(bookName).set(newBook).addOnSuccessListener(aVoid -> {
                clearInputField();
                Toast.makeText(AddBookActivity.this, "Book added Successfully", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> Toast.makeText(AddBookActivity.this, "Error adding Book!please contact admin", Toast.LENGTH_SHORT).show());
        } catch (Error e) {
            Log.d("ADD BOOK ACTIVITY", e.toString());
        }
    }

    private void clearInputField() {
        bookTitleTV.getText().clear();
        authorNameTv.getText().clear();
        descriptionTv.getText().clear();
        bookCountTv.getText().clear();
    }
}
