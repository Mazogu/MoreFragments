package com.example.micha.recyclefragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheeseFragment extends Fragment {

    List<String> cheeses = new ArrayList<>();
    public static final String TAG = CheeseFragment.class.getSimpleName();

    public CheeseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cheese, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: something");
        try {
            InputStream input = getActivity().getAssets().open("cheese_list");
            byte[] bytes = new byte[input.available()];
            input.read(bytes);
            String string = new String(bytes, "UTF-8");
            JSONObject json = new JSONObject(string);
            JSONArray jarray = json.getJSONArray("Cheeses");
            for (int i = 0; i < jarray.length() ; i++) {
                cheeses.add(jarray.getString(i));
            }
        }
        catch (IOException e) {
            Log.d(TAG, "onViewCreated: failure");
            e.printStackTrace();
        }
        catch (JSONException j){
            Log.d(TAG, "onViewCreated: Json failure");
            j.printStackTrace();
        }
        Log.d(TAG, "onViewCreated: "+ cheeses.toString());
    }

    private class CheeseView extends RecyclerView{
        public CheeseView(Context context) {
            super(context);
        }

    }



}
