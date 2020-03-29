package com.vjay.libararymanagement;

import android.app.Activity;
import android.content.Context;
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

public class ReturnApprovalAdapter extends ArrayAdapter<BookHistoryItem> {

    private Context context;
    Map<BookReturnStatus, String> returnStatusText = new HashMap<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ReturnApprovalAdapter(@NonNull Context context, @NonNull List<BookHistoryItem> objects) {
        super(context, 0, objects);
        this.context = context;
        returnStatusText.put(BOOK_RETURNED, "Book Returned");
        returnStatusText.put(YET_TO_RETURN, "Yet to return");
        returnStatusText.put(WAITING_FOR_LIBRARIAN_CONFIRMATION, "Waiting");
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.return_arroval_item, parent, false);

            BookingHistory book = getItem(position).getBookingHistory();

            TextView bookName = convertView.findViewById(R.id.rbtitle);
            TextView bookStatusDesc = convertView.findViewById(R.id.rbsubtitle);
            Button confirmButton = convertView.findViewById(R.id.raconfirm);
            bookName.setText(book.getBookName() + " booked by " + book.getStudentName());
            if (book.getBookReturnStatus().equals(BOOK_RETURNED)) {
                bookStatusDesc.setText("returned it on " + book.getReturnedAt());
                confirmButton.setEnabled(false);
            } else {
                bookStatusDesc.setText("request book return on " + book.getReturnedAt());
            }

            confirmButton.setOnClickListener(v -> {
                Map updateData = new HashMap();
                updateData.put("bookReturnStatus", BOOK_RETURNED);
                db.collection("libraryhistory").document(getItem(position).getItemKey()).update(updateData);
                getItem(position).getBookingHistory().setBookReturnStatus(BOOK_RETURNED);
                bookStatusDesc.setText(" returned it on " + book.getReturnedAt());
                notifyDataSetChanged();
                confirmButton.setEnabled(false);
            });

        }
        return convertView;
    }
}
