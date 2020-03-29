package com.vjay.libararymanagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vjay.libararymanagement.model.Book;

public class BookDetailActivity extends AppCompatActivity {
    TextView bookTitleTV;
    TextView authorNameTv;
    TextView descriptionTv;
    TextView bookCountTv;
    TextView bookAvailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookTitleTV = findViewById(R.id.bdBookname);
        authorNameTv = findViewById(R.id.bdAuthorName);
        descriptionTv = findViewById(R.id.bdDesc);
        bookCountTv = findViewById(R.id.bdBookCount);
        bookAvailTv = findViewById(R.id.bdBooksRemaining);
        Book bookeSelected = (Book) getIntent().getSerializableExtra("BOOK_SELECTED");
        if (bookeSelected != null) {
            bookTitleTV.setText("Book Title: " + bookeSelected.getBookName());
            authorNameTv.setText("Author Name: " + bookeSelected.getAuthorName());
            descriptionTv.setText("Description: " + bookeSelected.getDescription());
            bookCountTv.setText("No of Books: " + bookeSelected.getBookCount());
            bookAvailTv.setText("No. of Books availabele: " + bookeSelected.getBooksAvailableForStudent());
        }

    }
}
