package com.example.pawfectpicks.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pawfectpicks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<PetModel> list;
    private final RecyclerViewItemClick recyclerViewItemClick;

    public MyAdapter(Context context, ArrayList<PetModel> list, RecyclerViewItemClick recyclerViewItemClick) {
        this.context = context;
        this.list = list;
        this.recyclerViewItemClick=recyclerViewItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return new MyViewHolder(v, recyclerViewItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PetModel petModel = list.get(position);
//        Glide.with(holder.petPic.getContext()).load(petModel.getPetPic()).into(holder.petPic);
        String imageUrl=petModel.getPetPic();
        Picasso.get().load(imageUrl).into(holder.petPic);
        holder.name.setText(petModel.getPname());
        holder.age.setText(petModel.getPage());
        holder.type.setText(petModel.getPtype());
        holder.gender.setText(petModel.getPgender());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView petPic;
        TextView name,age, type,gender;

        public MyViewHolder(@NonNull View itemView, RecyclerViewItemClick recyclerViewItemClick) {
            super(itemView);

            petPic=itemView.findViewById(R.id.petPic);
            name=itemView.findViewById(R.id.petName);
            age=itemView.findViewById(R.id.petAge);
            type=itemView.findViewById(R.id.petType);
            gender=itemView.findViewById(R.id.petGender);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewItemClick !=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewItemClick.onItemClick(list.get(pos));
                        }
                    }
                }
            });
        }
    }
}
