package com.firedup.adobefirebasetaskreminder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class DoesAdapter extends RecyclerView.Adapter<DoesAdapter.MyViewHolder> {
    Context context;

    public DoesAdapter(Context context, ArrayList<MyDoes> myDoes) {
        this.context = context;
        this.myDoes = myDoes;
    }

    ArrayList<MyDoes> myDoes;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titledoes.setText(myDoes.get(position).getTitledoes());
        holder.descdoes.setText(myDoes.get(position).getDescdoes());
        holder.datedoes.setText(myDoes.get(position).getDatedoes());
        holder.timedoes.setText(myDoes.get(position).getTimedoes());

         final String getTitleDoes = myDoes.get(position).getTitledoes();
        final String getDescDoes = myDoes.get(position).getDescdoes();
        final String getDateDoes = myDoes.get(position).getDatedoes();
        final String getKeyDoes = myDoes.get(position).getKeydoes();
        final String getTimeDoes = myDoes.get(position).getTimedoes();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context,EditTaskDesk.class);
                aa.putExtra("titleDoes",getTitleDoes);
                aa.putExtra("descDoes",getDescDoes);
                aa.putExtra("dateDoes",getDateDoes);
                aa.putExtra("keyDoes",getKeyDoes);
                aa.putExtra("timeDoes",getTimeDoes);
                context.startActivity(aa);
            }
        });
    }



    @Override
    public int getItemCount() {
        return myDoes.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titledoes, descdoes, datedoes,timedoes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = itemView.findViewById(R.id.titledoes);
            descdoes =  itemView.findViewById(R.id.descdoes);
            datedoes =  itemView.findViewById(R.id.datedoes);
            timedoes = itemView.findViewById(R.id.timedoes);
        }
    }
}
