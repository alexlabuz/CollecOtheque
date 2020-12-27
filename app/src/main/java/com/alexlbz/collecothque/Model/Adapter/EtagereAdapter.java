package com.alexlbz.collecothque.Model.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.R;

import java.util.List;

public abstract class EtagereAdapter extends RecyclerView.Adapter<EtagereAdapter.ViewHolder> implements View.OnClickListener {

    private List<Etagere> etagereList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextRowName;
        private CardView mCardRowShelf;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextRowName = itemView.findViewById(R.id.row_name_shelf);
            this.mCardRowShelf = itemView.findViewById(R.id.row_card_shelf);
        }
    }

    public EtagereAdapter(List<Etagere> etagereList) {
        this.etagereList = etagereList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View etagereView = inflater.inflate(R.layout.row_list_shelf, parent, false);
        return new ViewHolder(etagereView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etagere e = etagereList.get(position);
        holder.mTextRowName.setText(e.getLibelle());
        holder.mCardRowShelf.setTag(e);
        holder.mCardRowShelf.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return etagereList.size();
    }

    @Override
    public abstract void onClick(View view);
}
