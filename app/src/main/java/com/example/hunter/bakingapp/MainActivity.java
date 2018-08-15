package com.example.hunter.bakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hunter.bakingapp.Adapter.RecipeAdapter;
import com.example.hunter.bakingapp.Data.IngredientsContract;
import com.example.hunter.bakingapp.Data.IngredientsDbHelper;
import com.example.hunter.bakingapp.Model.Ingredients;
import com.example.hunter.bakingapp.Model.Recipe;
import com.example.hunter.bakingapp.Model.Steps;
import com.facebook.stetho.Stetho;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener{
    private RecyclerView mrecyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList;
    public int[] num_Ingredients = new int[4];
    ArrayList<Ingredients> ingredientList;
    ArrayList<Steps> stepsList;
    int c = 0;
    int listSize;
    public static boolean tablet;
    private TextView errorView;
    private Button btn_reload;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        errorView = findViewById(R.id.show_error);
        btn_reload = findViewById(R.id.btn_error);
        mrecyclerView = findViewById(R.id.rv_mainlayout);
        recipeList = new ArrayList<>();

        if (!check_ConnectionStatus()) {
            showgagets();
            return;
        }

        tablet = isTablet();
        checkDevice();
        callJson();
    }

    private void showgagets() {
        errorView.setVisibility(View.VISIBLE);
        btn_reload.setVisibility(View.VISIBLE);
    }

    private boolean check_ConnectionStatus() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != cm) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                return info != null && info.isConnectedOrConnecting();
            }
            return false;


    }

    private boolean isTablet() {

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            int densityDpi = displayMetrics.densityDpi;

            double wInches = width / (double)densityDpi;
            double hInches = height / (double)densityDpi;

            double screenDiagonal = Math.sqrt(Math.pow(wInches, 2) + Math.pow(hInches, 2));

            return (Math.ceil(screenDiagonal) >= 7.0);
        }




    public void saveIngredientsData(ArrayList<Ingredients> ingredientList) {
        IngredientsDbHelper ingredientAppDbHelper = new IngredientsDbHelper(this);
        final SQLiteDatabase db = ingredientAppDbHelper.getReadableDatabase();
        String tableName = IngredientsContract.IngredientsEntry.TABLE_NAME;


        ContentValues contentValues = new ContentValues();
        listSize = ingredientList.size();
        num_Ingredients[c++] = listSize;
        for (int k = 0; k <listSize; k++){
            contentValues.put(IngredientsContract.IngredientsEntry.COLUMN_QUANTITY,
                    ingredientList.get(k).getQuantity());
            contentValues.put(IngredientsContract.IngredientsEntry.COLUMN_MEASUSE,
                    ingredientList.get(k).getMeasure());
            contentValues.put(IngredientsContract.IngredientsEntry.COLUMN_INGREDIENT,
                    ingredientList.get(k).getIngredients());

            getContentResolver().insert(IngredientsContract.IngredientsEntry.CONTENT_URI, contentValues);
        }
                  dataNumIngredients(num_Ingredients);
        }

private void dataNumIngredients(int[] num_Ingredients){
        SharedPreferences.Editor sharedPreferences= getSharedPreferences("numIng", 0).edit();
        sharedPreferences.putInt("numIngIdex0", num_Ingredients[0]);
        sharedPreferences.putInt("numIngIdex1", num_Ingredients[1]);
        sharedPreferences.putInt("numIngIdex2", num_Ingredients[2]);
        sharedPreferences.putInt("numIngIdex3", num_Ingredients[3]);
        sharedPreferences.apply();
        }


private void callJson() {
        final String Url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        StringRequest request = new StringRequest(Request.Method.GET, Url,
        new Response.Listener<String>() {
@Override
public void onResponse(String response) {
        try {
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jo = jsonArray.getJSONObject(i);

        int Recipe_Id = jo.getInt("id");

        String Recipe_Name = jo.getString("name");
        JSONArray Recipe_Ingredients = jo.getJSONArray("ingredients");
        ingredientList = new ArrayList<>();

        for (int j = 0; j < Recipe_Ingredients.length(); j++) {
        JSONObject obj_ingrdnt = Recipe_Ingredients.getJSONObject(j);
        String quantity = obj_ingrdnt.getString("quantity");
        String measure = obj_ingrdnt.getString("measure");
        String ingredients = obj_ingrdnt.getString("ingredient");
        ingredientList.add(new Ingredients(quantity,measure,ingredients));
        }
        JSONArray Recipe_Steps = jo.getJSONArray("steps");
        stepsList = new ArrayList<>();
        for (int k = 0; k < Recipe_Steps.length(); k++) {
        JSONObject obj_steps = Recipe_Steps.getJSONObject(k);
        int id = obj_steps.getInt("id");
        String shortDescrptn = obj_steps.getString("shortDescription");
        String deScrptn = obj_steps.getString("description");
        String vdoUrl = obj_steps.getString("videoURL");
        String thmbUrl = obj_steps.getString("thumbnailURL");

        stepsList.add(new Steps(id,shortDescrptn,deScrptn,vdoUrl,thmbUrl));
        }
        int servings = Integer.parseInt(jo.getString("servings"));
        String image = jo.getString("image");
        recipeList.add(new Recipe(Recipe_Id,Recipe_Name,ingredientList,stepsList,servings,image));
        saveIngredientsData(ingredientList);
        }

        adapter = new RecipeAdapter(MainActivity.this,recipeList);
        mrecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
        adapter.notifyDataSetChanged();

        } catch (JSONException e) {
        e.printStackTrace();
        }

        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    }

        });
        mRequestQueue.add(request);
        }


@Override
public void OnItemClick(int position) {
        Intent detailIntent = new Intent (this,DetailActivity.class);
        detailIntent.putExtra("ReceipeList", recipeList.get(position));

    if(isTablet()){
        detailIntent.putExtra("Tablet",tablet);
        }
        startActivity(detailIntent);
        }

    public void reload(View view) {
        if (check_ConnectionStatus()) {
            errorView.setVisibility(View.INVISIBLE);
            btn_reload.setVisibility(View.INVISIBLE);
            checkDevice();
            callJson();
        } else {
            Toast.makeText(this, "Check Internet/Network", Toast.LENGTH_LONG).show();
        }
    }

    private void checkDevice() {
//        tablet = isTablet();

        if (!tablet) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mrecyclerView.setLayoutManager(layoutManager);
        } else {
            mrecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mrecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        mrecyclerView.setHasFixedSize(true);
        mRequestQueue = Volley.newRequestQueue(this);
    }
}
