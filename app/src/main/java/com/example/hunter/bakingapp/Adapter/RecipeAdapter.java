package com.example.hunter.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hunter.bakingapp.Model.Recipe;
import com.example.hunter.bakingapp.R;

import java.util.List;



public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private final Context mcontext;
    private final List<Recipe> recipeList;
    OnItemClickListener mlistner;


    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistner = listener;
    }

    public RecipeAdapter(Context mcontext, List<Recipe> recipeList) {
        this.mcontext = mcontext;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.list_main,parent,false);
        return new RecipeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe currentItem = recipeList.get(position);
        String name = currentItem.getRecipe_name();

        holder.tv_main.setText(name);
        switch (position){
            case 0: holder.iv_main.setImageResource(R.drawable.nutella_pie);
                break;
            case 1: holder.iv_main.setImageResource(R.drawable.brownie);
                break;
            case 2: holder.iv_main.setImageResource(R.drawable.yellow_cake);
                break;
            case 3: holder.iv_main.setImageResource(R.drawable.cheesecake);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        final ImageView iv_main;
        final TextView tv_main;

        public RecipeHolder(View itemView) {
            super(itemView);
            iv_main = itemView.findViewById(R.id.iv_image);
            tv_main = itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistner.OnItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
