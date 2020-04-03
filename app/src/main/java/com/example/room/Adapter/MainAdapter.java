package com.example.room.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.MainContract;
import com.example.room.Model.DataDiri;
import com.example.room.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context context;
    private List<DataDiri> list;
    private MainContract.view view;

    public MainAdapter(Context context, List<DataDiri> list, MainContract.view view) {
        this.context = context;
        this.list = list;
        this.view = view;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_row,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final DataDiri item = list.get(position);
        holder.tvNama.setText(item.getName());
        holder.tvAlamat.setText(item.getAddress());
        holder.tvGender.setText(String.valueOf(item.getGender()));
        holder.id.setText(String.valueOf(item.getId()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.editData(item);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                view.deleteData(item);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvAlamat, tvGender, id;
        private CardView cardView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_item_nama);
            tvAlamat = itemView.findViewById(R.id.tv_item_alamat);
            tvGender = itemView.findViewById(R.id.tv_item_gender);
            id = itemView.findViewById(R.id.tv_item_id);
            cardView = itemView.findViewById(R.id.cv_item);
        }
    }
}
