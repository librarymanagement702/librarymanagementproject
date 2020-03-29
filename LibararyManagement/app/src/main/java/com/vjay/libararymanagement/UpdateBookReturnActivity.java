package com.vjay.libararymanagement;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vjay.libararymanagement.model.AppUser;
import com.vjay.libararymanagement.model.BookHistoryItem;
import com.vjay.libararymanagement.model.BookReturnStatus;
import com.vjay.libararymanagement.model.BookingHistory;

import java.util.ArrayList;
import java.util.List;

public class UpdateBookReturnActivity extends AppCompatActivity {
    private ListView booksAvailableListElement;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<BookHistoryItem> bookSelectionList;
    AppUser loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book_return);
        loginUser = (AppUser) getIntent().getSerializableExtra(MainActivity.LOGIN_USER_ARG);
        booksAvailableListElement = findViewById(R.id.approvalList);
        db.collection("libraryhistory").get().addOnCompleteListener(task -> {
            bookSelectionList = new ArrayList<>();
            if (task.isComplete()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    BookingHistory bookingHistory = document.toObject(BookingHistory.class);
                    BookHistoryItem item = new BookHistoryItem();
                    if (bookingHistory.getBookReturnStatus().equals(BookReturnStatus.WAITING_FOR_LIBRARIAN_CONFIRMATION)) {
                        item.setBookingHistory(bookingHistory);
                        item.setItemKey(document.getId());
                        bookSelectionList.add(item);
                    }
                }
                ReturnApprovalAdapter adapter = new ReturnApprovalAdapter(UpdateBookReturnActivity.this, bookSelectionList);
                adapter.notifyDataSetChanged();
                booksAvailableListElement.setAdapter(adapter);
            }
        });
    }
}
