package com.example.nikit.news.ui.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nikit.news.R;
import com.example.nikit.news.entities.Source;
import com.example.nikit.news.util.JsonUtil;
import com.example.nikit.news.ui.adapters.SourcesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourcesFragment extends Fragment {
    private RecyclerView rvSources;
    private final SourcesAdapter sourcesAdapter;

    public SourcesFragment() {
        sourcesAdapter = new SourcesAdapter();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sources, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSources = (RecyclerView)view.findViewById(R.id.rv_sources);
        rvSources.setAdapter(sourcesAdapter);
        rvSources.setLayoutManager(new LinearLayoutManager(view.getContext()));

        new LoadNewsAsyncTask().execute();

    }

    // Loading news from network
    class LoadNewsAsyncTask extends AsyncTask<Void, Void, Void> {
        private String jsonString;
        private ProgressBar progressBar;
        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(jsonString != null && jsonString.length() > 0){
                sourcesAdapter.swapData(JsonUtil.getSourcesFromJson(jsonString));
            }
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = JsonUtil.loadJsonString("https://newsapi.org/v1/sources");
            Log.d("jsonString", jsonString);
            return null;
        }
    }


}
