package com.example.hunter.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hunter.bakingapp.Data.IngredientsContract;
import com.example.hunter.bakingapp.R;

import java.util.ArrayList;

public class WidgetConfigureActivity extends AppCompatActivity {

    public static String widgetText, widgetData;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    public static ArrayList<String> data_Ingredient = new ArrayList<>();
    private static int[] num_IngredientsWidget = new int[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_configure);

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();

        Log.d("Extras", String.valueOf(extras));

        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            Log.d("hello","goodbye");
            finish();
        }

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final ListView listView = findViewById(R.id.list);

        String[] values = new String[]{"Nutella Pie", "Brownies", "YellowCake", "CheeseCake"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                widgetText = (String) listView.getItemAtPosition(position);
                loadListItem(getApplicationContext());
                widgetData(getApplicationContext(), position);
                createWidget(getApplicationContext());
            }
        });


    }

    private void loadListItem(Context context) {
        Cursor cursor = context.getContentResolver().query(IngredientsContract.IngredientsEntry.CONTENT_URI,
                null,
                null,
                null,
                IngredientsContract.IngredientsEntry._ID);


        if(cursor != null){
            while(cursor.moveToNext()){
                data_Ingredient.add(
                        cursor.getString(cursor.getColumnIndex(IngredientsContract.IngredientsEntry.COLUMN_INGREDIENT)));
            }
        }
        cursor.close();

    }

    public static String widgetText(){

        return widgetText;
    }

    private void createWidget(Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        AppWidget.updateAppWidget(context,appWidgetManager, mAppWidgetId);

        Intent result = new Intent();

        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

        setResult(RESULT_OK, result);
        finish();


    }

    private void widgetData(Context context, int position){
        int PosInit =0;
        int PosFinal =0;
        loadNumofIng(context);
        switch (position){
            case 0:
                PosInit= 0;
                PosFinal = num_IngredientsWidget[position];
                Log.d("control0", String.valueOf(PosFinal));
                break;
            case 1:
                PosInit=  num_IngredientsWidget[0];
                PosFinal = num_IngredientsWidget[position] + num_IngredientsWidget[position+1]-1;
                Log.d("control1", String.valueOf(PosFinal));
                break;
            case 2:
                PosInit = num_IngredientsWidget[position-1] + num_IngredientsWidget[position]-1;
                PosFinal = num_IngredientsWidget[position-1] + num_IngredientsWidget[position]-1 + num_IngredientsWidget[position];
                Log.d("control2", String.valueOf(PosFinal));
                break;
            case 3:
                PosInit = num_IngredientsWidget[position-1] + num_IngredientsWidget[position]+1 + num_IngredientsWidget[position];
                PosFinal = num_IngredientsWidget[position-1] + num_IngredientsWidget[position]+1 + num_IngredientsWidget[position] + num_IngredientsWidget[position];
                Log.d("control3", String.valueOf(PosFinal));
                break;
        }

        StringBuilder stringBuilder= new StringBuilder();
        int cont = 1;

        for (int i = PosInit; i< PosFinal; i++){
            stringBuilder.append(cont).append(" - ").append(data_Ingredient.get(i)).append("\n");
            cont++;
        }
        WidgetConfigureActivity.widgetData = stringBuilder.toString();



    }

    private void loadNumofIng(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("numIng",0);
        num_IngredientsWidget[0] = sharedPreferences.getInt("numIngIdex0", 0);
//        Log.d("ingreO", String.valueOf(numIngredientsWidget[0]));
//        Log.d("ingre1", String.valueOf(numIngredientsWidget[1]));
        num_IngredientsWidget[1] = sharedPreferences.getInt("numIngIdex1", 0);
        num_IngredientsWidget[2] = sharedPreferences.getInt("numIngIdex2", 0);
//        Log.d("ingre2", String.valueOf(numIngredientsWidget[2]));
        num_IngredientsWidget[3] = sharedPreferences.getInt("numIngIdex3", 0);
//        Log.d("ingre3", String.valueOf(numIngredientsWidget[3]));

    }
}
