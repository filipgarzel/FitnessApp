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
import com.example.fitness.scenario.ShowScenarioActivity;

import java.util.ArrayList;

public class ScenariosAdapter extends RecyclerView.Adapter<ScenariosAdapter.MyViewHolder> {
    private Context context;
    private ArrayList nameOfScenario, descOfScenario;

    public ScenariosAdapter(Context context, ArrayList nameOfScenario, ArrayList descOfScenario){
        this.context = context;
        this.nameOfScenario = nameOfScenario;
        this.descOfScenario = descOfScenario;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.scenario_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.nameOfSc.setText(String.valueOf(nameOfScenario.get(position)));
        holder.descOfSc.setText(String.valueOf(descOfScenario.get(position)));
        holder.scenRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowScenarioActivity.class);
                intent.putExtra("passNameScen", String.valueOf(nameOfScenario.get(holder.getAdapterPosition())));
                intent.putExtra("passDescriptionScen", String.valueOf(descOfScenario.get(holder.getAdapterPosition())));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameOfScenario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout scenRowLayout;
        TextView nameOfSc, descOfSc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfSc = itemView.findViewById(R.id.namee);
            descOfSc = itemView.findViewById(R.id.descript);
            scenRowLayout = itemView.findViewById(R.id.scenRowLayout);

        }
    }
}
