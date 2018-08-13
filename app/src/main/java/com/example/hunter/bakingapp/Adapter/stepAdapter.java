package com.example.hunter.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hunter.bakingapp.Model.Steps;
import com.example.hunter.bakingapp.R;

import java.util.List;

public class stepAdapter extends RecyclerView.Adapter<stepAdapter.stepHolder> {
    private final Context mcontext;
    private final List<Steps>stepsList;
    OnItemClickListener mlistner;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistner = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public stepAdapter(Context mcontext, List<Steps>stepsList) {
        this.mcontext = mcontext;
        this.stepsList = stepsList;
    }

    @NonNull
    @Override
    public stepAdapter.stepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.list_detail,parent,false);
        return new stepAdapter.stepHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull stepAdapter.stepHolder holder, int position) {
        Steps currentItem = stepsList.get(position);
//        String name = currentItem.getRecipe_name();
        String shtDesc = currentItem.getShortDescrptn();
        String deSc = currentItem.getDeScrptn();

        holder.tv_steps_name.setText(shtDesc);
        holder.tv_steps.setText(deSc);
//        switch (position){
//            case 0: holder.iv_main.setImageResource(R.drawable.nutella_pie);
//                break;
//            case 1: holder.iv_main.setImageResource(R.drawable.brownie);
//                break;
//            case 2: holder.iv_main.setImageResource(R.drawable.yellow_cake);
//                break;
//            case 3: holder.iv_main.setImageResource(R.drawable.cheesecake);
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class stepHolder extends RecyclerView.ViewHolder{
            TextView tv_steps_name;
            TextView tv_steps;

        public stepHolder(View itemView) {
            super(itemView);
            tv_steps_name = itemView.findViewById(R.id.tv_steps_name);
            tv_steps = itemView.findViewById(R.id.tv_steps);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistner.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
