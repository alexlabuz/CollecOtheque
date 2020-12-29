package com.alexlbz.collecothque.Model.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexlbz.collecothque.Model.Entity.Collection;
import com.alexlbz.collecothque.R;

import java.util.List;

public abstract class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private List<Collection> collectionList;

    public static final Integer DELETE_COLLECTION = 0;
    public static final Integer UPDATE_COLLECTION = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextRowName;
        private TextView mTextRowColor;
        private Button mBtUpdate;
        private Button mBtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextRowName = itemView.findViewById(R.id.row_name_collection);
            this.mTextRowColor = itemView.findViewById(R.id.row_color_collection);
            this.mBtUpdate = itemView.findViewById(R.id.row_bt_update_collection);
            this.mBtDelete = itemView.findViewById(R.id.row_bt_delete_collection);
        }
    }

    public CollectionAdapter(List<Collection> collectionList){
        this.collectionList = collectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View collectionView = inflater.inflate(R.layout.row_list_collection, parent, false);
        return new ViewHolder(collectionView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Collection c = collectionList.get(position);

        holder.mTextRowName.setText(c.getLibelle());
        holder.mTextRowColor.setText("Couleur : " + c.getCouleur());
        holder.mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(DELETE_COLLECTION, c);
            }
        });
        holder.mBtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(UPDATE_COLLECTION, c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    /**
     * Fonction qui est déclanché lors de l'appui sur le bouton "modifier" ou "supprimer" d'une collection
     * @param action action "supprimer" ou "modifier"
     * @param collection collection à modifier ou à supprimer
     */
    public abstract void click(Integer action, Collection collection);
}
