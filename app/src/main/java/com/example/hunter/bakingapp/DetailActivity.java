package com.example.hunter.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hunter.bakingapp.Adapter.ingredientAdapter;
import com.example.hunter.bakingapp.Adapter.stepAdapter;
import com.example.hunter.bakingapp.Model.Ingredients;
import com.example.hunter.bakingapp.Model.Recipe;
import com.example.hunter.bakingapp.Model.Steps;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements stepAdapter.OnItemClickListener {
    ArrayList<Steps> steps;
     ArrayList<Ingredients> Ingredients;
     boolean Tab;
    String Name;
     FragmentManager fm;
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView image = findViewById(R.id.iv_image);
        TextView title = findViewById(R.id.tv_title);
        RecyclerView rv_steps = findViewById(R.id.rv_steps);
        RecyclerView rv_ingredients = findViewById(R.id.rv_ingredients);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_steps.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager_ingre = new LinearLayoutManager(this);
        rv_ingredients.setLayoutManager(layoutManager_ingre);

        Intent intent = getIntent();
        Recipe recipelist = intent.getParcelableExtra("ReceipeList");
        int Id = recipelist.getRecipe_id();
        Tab = intent.getBooleanExtra("Tablet",false);
        Name = recipelist.getRecipe_name();
        steps = recipelist.getSteps();
        Ingredients = recipelist.getIngredients();

        switch (Id)
        {
            case 1: image.setImageResource(R.drawable.nutella_pie);
                break;
            case 2: image.setImageResource(R.drawable.brownie);
                break;
            case 3: image.setImageResource(R.drawable.yellow_cake);
                break;
            case 4: image.setImageResource(R.drawable.cheesecake);
                break;
        }

        title.setText(Name);
        setTitle(Name);
        stepAdapter sAdpt = new stepAdapter(this, steps);
        rv_steps.setAdapter(sAdpt);
        sAdpt.setOnItemClickListener(this);
        sAdpt.notifyDataSetChanged();

        ingredientAdapter iAdpt = new ingredientAdapter(this, Ingredients);
        rv_ingredients.setAdapter(iAdpt);
        iAdpt.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {

        if(Tab){
            fm = getSupportFragmentManager();
            StepsFragment fragment = (StepsFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);
            fragment = StepsFragment.newInstance(steps,position);
            fm.beginTransaction().replace(R.id.direction_fragment_container, fragment,TAG_RETAINED_FRAGMENT).commit();
        }else
            {


            Intent stepIntent = new Intent(this, StepsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("stepList", steps);
            bundle.putInt("stepPosition", position);
            bundle.putString("Name",Name);
            stepIntent.putExtras(bundle);
            startActivity(stepIntent);
        }
    }
}
