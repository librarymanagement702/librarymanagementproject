package com.vjay.libararymanagement;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;
import com.vjay.libararymanagement.model.BookHistoryItem;
import com.vjay.libararymanagement.model.BookReturnStatus;
import com.vjay.libararymanagement.model.BookingHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vjay.libararymanagement.model.BookReturnStatus.BOOK_RETURNED;
import static com.vjay.libararymanagement.model.BookReturnStatus.WAITING_FOR_LIBRARIAN_CONFIRMATION;
import static com.vjay.libararymanagement.model.BookReturnStatus.YET_TO_RETURN;

public class BookingHistoryAdapter extends ArrayAdapter<BookHistoryItem> {
    private Context context;
    Map<BookReturnStatus, String> returnStatusText = new HashMap<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BookingHistoryAdapter(@NonNull Context context, @NonNull List<BookHistoryItem> objects) {
        super(context, 0, objects);
        this.context = context;
        returnStatusText.put(BOOK_RETURNED, "Book Returned");
        returnStatusText.put(YET_TO_RETURN, "Yet to return");
        returnStatusText.put(WAITING_FOR_LIBRARIAN_CONFIRMATION, "Waiting");
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.book_availed_item, parent, false);
        }
        BookingHistory book = getItem(position).getBookingHistory();

        TextView bookName = convertView.findViewById(R.id.bhBookName);
        TextView bookStatus = convertView.findViewById(R.id.bhBookStatus);
        bookStatus.setTextColor(getColorCode(book.getBookReturnStatus()));
        bookName.setText(book.getBookName());
        bookStatus.setText(returnStatusText.get(book.getBookReturnStatus()));
        Button returnBookButton = convertView.findViewById(R.id.returnBook);
        if (!book.getBookReturnStatus().equals(YET_TO_RETURN)) {
            returnBookButton.setEnabled(false);
        }
        returnBookButton.setOnClickListener(v -> {
            Map updateData = new HashMap();
            updateData.put("returnedAt", AppUtil.getTodayDateString());
            updateData.put("bookReturnStatus", WAITING_FOR_LIBRARIAN_CONFIRMATION);
            db.collection("libraryhistory").document(getItem(position).getItemKey()).update(updateData);
            getItem(position).getBookingHistory().setBookReturnStatus(WAITING_FOR_LIBRARIAN_CONFIRMATION);
            getItem(position).getBookingHistory().setReturnedAt(AppUtil.getTodayDateString());
            notifyDataSetChanged();
            returnBookButton.setEnabled(false);
        });
        return convertView;
    }

    public int getColorCode(BookReturnStatus status) {
        if (status == BOOK_RETURNED) {
            return Color.GREEN;
        } else if (status == WAITING_FOR_LIBRARIAN_CONFIRMATION) {
            return Color.DKGRAY;
        } else {
            return Color.RED;
        }
    }

}
