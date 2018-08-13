package com.example.hunter.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hunter.bakingapp.Model.Ingredients;
import com.example.hunter.bakingapp.R;

import java.util.List;

public class ingredientAdapter extends RecyclerView.Adapter<ingredientAdapter.ingredientHolder> {
    private final Context mcontext;
    private final List<Ingredients> ingredientsList;

    public ingredientAdapter(Context mcontext, List<Ingredients> ingredientsList) {
        this.mcontext = mcontext;
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public ingredientAdapter.ingredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.list_ingredients,parent,false);
        return new ingredientAdapter.ingredientHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ingredientAdapter.ingredientHolder holder, int position) {
        Ingredients currentItem = ingredientsList.get(position);
        String quantity = currentItem.getQuantity();
        String measure = currentItem.getMeasure();
        String ingredients = currentItem.getIngredients();

        holder.tv_ingredients.setText(ingredients);
        holder.tv_measure.setText(measure);
        holder.tv_quantity.setText(quantity);
// String name = currentItem.getRecipe_name();
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public class ingredientHolder extends RecyclerView.ViewHolder{
        TextView tv_ingredients;
        TextView tv_quantity;
        TextView tv_measure;
        public ingredientHolder(View itemView)
        {
            super(itemView);
            tv_ingredients = itemView.findViewById(R.id.tv_ingr_name);
            tv_quantity = itemView.findViewById(R.id.tv_quantity_name);
            tv_measure = itemView.findViewById(R.id.tv_measure_name);
        }
    }
}
