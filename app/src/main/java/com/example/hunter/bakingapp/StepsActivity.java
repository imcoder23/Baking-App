package com.example.hunter.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.hunter.bakingapp.Model.Steps;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {
    @Nullable @BindView(R.id.step_number)TextView mStepNum;
    @Nullable @BindView(R.id.prev_step)ImageButton mPrevButton;
    @Nullable @BindView(R.id.next_step)ImageButton mNextButton;

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private StepsFragment fragment;
    ArrayList<Steps> sList;
    int stepPosition;
    String Name;
    FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        sList = bundle.getParcelableArrayList("stepList");
        stepPosition = bundle.getInt("stepPosition");
        Name = bundle.getString("Name");
        setTitle(Name);

        fm = getSupportFragmentManager();
        fragment = (StepsFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition--;
                fragment = StepsFragment.newInstance(sList,stepPosition);
                fm.beginTransaction().replace(R.id.direction_fragment_container,fragment, TAG_RETAINED_FRAGMENT).commit();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition++;
                fragment = StepsFragment.newInstance(sList,stepPosition);
                fm.beginTransaction().replace(R.id.direction_fragment_container,fragment, TAG_RETAINED_FRAGMENT).commit();
            }
        });

        if(fragment == null){
            fragment = StepsFragment.newInstance(sList,stepPosition);
            fm.beginTransaction().replace(R.id.direction_fragment_container,fragment,TAG_RETAINED_FRAGMENT).commit();
        }
    }
}