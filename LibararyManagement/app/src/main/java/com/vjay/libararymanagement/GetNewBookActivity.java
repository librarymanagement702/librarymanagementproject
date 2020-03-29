package com.vjay.libararymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vjay.libararymanagement.model.AppUser;
import com.vjay.libararymanagement.model.Book;
import com.vjay.libararymanagement.model.BookReturnStatus;
import com.vjay.libararymanagement.model.BookSelection;
import com.vjay.libararymanagement.model.BookingHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetNewBookActivity extends AppCompatActivity {
    private ListView booksAvailableListElement;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<BookSelection> bookSelectionList;

    AppUser loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_new_book);
        loginUser = (AppUser) getIntent().getSerializableExtra(MainActivity.LOGIN_USER_ARG);
        booksAvailableListElement = findViewById(R.id.booksAvailableLs);
        db.collection("books").get().addOnCompleteListener(task -> {
            bookSelectionList = new ArrayList<>();
            if(task.isComplete()){
                for(QueryDocumentSnapshot document : task.getResult()) {
                    Book book = document.toObject(Book.class);
                    BookSelection bs = new BookSelection();
                    bs.setBook(book);
                    bs.setSelected(false);
                    bookSelectionList.add(bs);
                }
                BookSelectionAdapter mProductAdapter = new BookSelectionAdapter(GetNewBookActivity.this, bookSelectionList);
                mProductAdapter.notifyDataSetChanged();
                booksAvailableListElement.setAdapter(mProductAdapter);
            }

        });
    }

    public void orderBooks(View view){
        List<BookSelection> booksSelected = bookSelectionList.stream()
                .filter(book -> book.isSelected())
                .collect(Collectors.toList());
        List<String> booksSelectedStr = booksSelected.stream()
                .map(book-> book.getBook().getBookName())
                .collect(Collectors.toList());

        Toast.makeText(GetNewBookActivity.this, booksSelectedStr.toString(), Toast.LENGTH_SHORT).show();
        for (BookSelection bs: booksSelected){
            updateBookSelectionForUser(bs);
            updateBookCount(bs.getBook());
        }

    }

    public void updateBookSelectionForUser(BookSelection bookSelected){

        BookingHistory historyEntry = new BookingHistory();
        historyEntry.setBookName(bookSelected.getBook().getBookName());
        historyEntry.setAuthorName(bookSelected.getBook().getAuthorName());
        historyEntry.setStudentName(loginUser.getName());
        historyEntry.setBookedAt(AppUtil.getTodayDateString());
        historyEntry.setBookReturnStatus(BookReturnStatus.YET_TO_RETURN);
        db.collection("libraryhistory").add(historyEntry);
    }

    public void updateBookCount(Book book){
        book.availOneUnit();
        db.collection("books").document(book.getBookName()).set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                clearInputField();
                Toast.makeText(GetNewBookActivity.this, "Book Ordered Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GetNewBookActivity.this, "Error ordering Book!please contact admin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInputField(){

    }
}
