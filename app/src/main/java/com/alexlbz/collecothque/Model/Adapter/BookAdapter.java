package com.alexlbz.collecothque.Model.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexlbz.collecothque.Model.Entity.Livre;
import com.alexlbz.collecothque.Model.RequestDatabase;
import com.alexlbz.collecothque.R;
import com.android.volley.VolleyError;

import java.util.List;

public abstract class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements View.OnClickListener{

    private List<Livre> bookList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardViewBook;
        private TextView mTextRowName;
        private TextView mTextRowPublisher;
        private ImageView mImageRowBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mCardViewBook = itemView.findViewById(R.id.row_card_book);
            this.mTextRowName = itemView.findViewById(R.id.row_name_book);
            this.mTextRowPublisher = itemView.findViewById(R.id.row_publishers_book);
            this.mImageRowBook = itemView.findViewById(R.id.row_image_book);
        }
    }

    public BookAdapter(List<Livre> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View bookView = inflater.inflate(R.layout.row_list_book, parent, false);
        return new BookAdapter.ViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookAdapter.ViewHolder holder, int position) {
        Livre l = bookList.get(position);
        holder.mCardViewBook.setTag(l);
        holder.mCardViewBook.setOnClickListener(this);
        holder.mTextRowName.setText(l.getTitre());
        holder.mTextRowPublisher.setText(l.getEditeur());

        RequestDatabase request = new RequestDatabase(context) {
            @Override
            public void resultRequest(Object o, Integer requestId) {
                holder.mImageRowBook.setImageBitmap((Bitmap) o);
            }

            @Override
            public void errorRequest(VolleyError error, Integer requestId) {}
        };
        request.recupImage("https://covers.openlibrary.org/b/id/" + l.getImage() + ".jpg", 0);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public abstract void onClick(View view);
}
