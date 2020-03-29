package com.vjay.libararymanagement;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vjay.libararymanagement.model.Book;

import java.util.List;

class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(@NonNull Context context, List<Book> object) {
        super(context, 0, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.book_list, parent, false);
        }
        Book book = getItem(position);

        TextView prod = (TextView) convertView.findViewById(R.id.bookName);
        prod.setText(book.getBookName());
        return convertView;
    }
}
