package com.example.fitness.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitness.R;
import com.example.fitness.base.ShowExActivity;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterForRecycler extends RecyclerView.Adapter<AdapterForRecycler.MyViewHolder> {

    private Context context;
    private ArrayList array_name, array_description, array_category, array_level;
    int position;
    Intent i;


    public AdapterForRecycler(Context context, ArrayList array_name, ArrayList array_description, ArrayList array_category, ArrayList array_level, Intent i){
        this.context = context;
        this.array_name = array_name;
        this.array_description = array_description;
        this.array_category = array_category;
        this.array_level = array_level;
        this.i = i;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name_txt.setText(String.valueOf(array_name.get(position)));
        holder.category_txt.setText(String.valueOf(array_category.get(position)));
        holder.level_txt.setText(String.valueOf(array_level.get(position)));
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowExActivity.class);
                intent.putExtra("passName", String.valueOf(array_name.get(holder.getAdapterPosition())));
                String aaa = i.getStringExtra("pass");
                String bbb = i.getStringExtra("passName");
                String ccc = i.getStringExtra("passDescription");
                intent.putExtra("passN", bbb);
                intent.putExtra("passD", ccc);
                intent.putExtra("passDescription", String.valueOf(array_description.get(holder.getAdapterPosition())));
                if(Objects.equals(aaa, "yup")){
                    intent.putExtra("give", "go");
                }

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return array_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rowLayout;
        TextView name_txt, category_txt, level_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name);
            category_txt = itemView.findViewById(R.id.category);
            level_txt = itemView.findViewById(R.id.lvl);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}
