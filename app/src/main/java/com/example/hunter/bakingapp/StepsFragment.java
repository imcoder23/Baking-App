package com.example.hunter.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hunter.bakingapp.Model.Steps;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepsFragment extends Fragment {
    @BindView(R.id.exo_player)SimpleExoPlayerView msimpleExoPlayerView;
    @BindView(R.id.fragment_direction_layout)ScrollView layout;
    @BindView(R.id.tv_item_detail_ingredients)TextView directions;
    @BindView(R.id.tv_title)TextView title;

    SimpleExoPlayer exoPlayer;
    private Unbinder unbinder;
    ArrayList<Steps> sList;
    int stepPosition;

    public static StepsFragment newInstance(ArrayList<Steps> sList, int stepPosition) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("StepList",sList);
        args.putInt("Position",stepPosition);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sList = getArguments().getParcelableArrayList("StepList");
        stepPosition = getArguments().getInt("Position");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direction,container,false);
        unbinder = ButterKnife.bind(this, view);

        String Url = sList.get(stepPosition).getVdoUrl();
        directions.setText(sList.get(stepPosition).getDeScrptn());
        title.setText(sList.get(stepPosition).getShortDescrptn());

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);

            Uri videoUrl = Uri.parse(Url);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("Exo");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoUrl, dataSourceFactory, extractorsFactory, null, null);

            msimpleExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }catch(Exception e){
            Log.e("Exoplayer","Exoplayer_Help");
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayer.release();
    }
}
