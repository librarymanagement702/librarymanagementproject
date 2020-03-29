package com.vjay.libararymanagement;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vjay.libararymanagement.model.Book;
import com.vjay.libararymanagement.model.BookSelection;

import java.util.List;

public class BookSelectionAdapter extends ArrayAdapter<BookSelection> {
    private Context context;

    public BookSelectionAdapter(@NonNull Context context, List<BookSelection> object) {
        super(context, 0, object);
        this.context = context;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.book_selection, parent, false);
        }
        Book book = getItem(position).getBook();

        CheckBox checkBox = convertView.findViewById(R.id.cb);
        checkBox.setText(book.getBookName());
        checkBox.setChecked(getItem(position).isSelected());
        checkBox.setOnClickListener(v -> {
            boolean isSeleceted = getItem(position).isSelected();
            getItem(position).setSelected(!isSeleceted);
        });
        return convertView;
    }
}
