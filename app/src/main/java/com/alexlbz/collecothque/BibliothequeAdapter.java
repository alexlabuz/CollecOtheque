package com.alexlbz.collecothque;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexlbz.collecothque.Model.Bibliotheque;

import java.util.List;

public abstract class BibliothequeAdapter extends RecyclerView.Adapter<BibliothequeAdapter.ViewHolder> implements View.OnClickListener{

    private List<Bibliotheque> bibliothequeList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextRowName;
        private CardView mCardRowLibrary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextRowName = itemView.findViewById(R.id.row_name_library);
            this.mCardRowLibrary = itemView.findViewById(R.id.row_card_library);
        }
    }

    public BibliothequeAdapter(List<Bibliotheque> bibliothequeList) {
        this.bibliothequeList = bibliothequeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View bibliothequeView = inflater.inflate(R.layout.row_list_library, parent, false);
        return new ViewHolder(bibliothequeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bibliotheque b = bibliothequeList.get(position);
        holder.mTextRowName.setText(b.getName());
        holder.mCardRowLibrary.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return bibliothequeList.size();
    }

    @Override
    public abstract void onClick(View view);

}
