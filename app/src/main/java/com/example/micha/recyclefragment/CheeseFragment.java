package com.example.micha.recyclefragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        RecyclerView recycleCheese = view.findViewById(R.id.cheeseView);
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

    public static class CheeseAdapter extends RecyclerView.Adapter<CheeseHolder>{
        List<String> cheeseList;

        public CheeseAdapter(List<String> cheeseList) {
            this.cheeseList = cheeseList;
        }

        @Override
        public CheeseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
            return new CheeseHolder(view);
        }

        @Override
        public void onBindViewHolder(CheeseHolder holder, int position) {
            holder.text.setText(cheeseList.get(position));
        }

        @Override
        public int getItemCount() {
            return cheeseList.size();
        }

    }

    public static class CheeseHolder extends RecyclerView.ViewHolder{

        private final TextView text;

        public CheeseHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.cheeseText);
        }
    }



}
