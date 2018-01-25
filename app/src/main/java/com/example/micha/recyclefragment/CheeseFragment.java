package com.example.micha.recyclefragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheeseFragment extends Fragment {

    private List<String> cheeses = new ArrayList<>();
    public static final String TAG = CheeseFragment.class.getSimpleName();
    private RecyclerView recycleCheese;
    private EditText filter;

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
        recycleCheese = view.findViewById(R.id.cheeseView);
        filter = view.findViewById(R.id.cheeseFilter);
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
            e.printStackTrace();
        }
        catch (JSONException j){
            j.printStackTrace();
        }
        Collections.sort(cheeses);
        final CheeseAdapter cheeseAdapter = new CheeseAdapter(cheeses);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recycleCheese.setAdapter(cheeseAdapter);
        recycleCheese.setLayoutManager(manager);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2==0){
                    cheeseAdapter.resetCheeseList();
                    Log.d(TAG, "onTextChanged: "+ cheeseAdapter.getCheeseList().toString());
                }
                else{
                    cheeseAdapter.filterCheeseList(charSequence);
                    Log.d(TAG, "onTextChanged: "+ cheeseAdapter.getCheeseList().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static class CheeseAdapter extends RecyclerView.Adapter<CheeseHolder>{
        private List<String> cheeseList;
        private final List<String> original;

        public CheeseAdapter(List<String> cheeseList) {
            this.cheeseList = cheeseList;
            original = new ArrayList<>();
            original.addAll(cheeseList);
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

        public List<String> getCheeseList() {
            return cheeseList;
        }

        public void resetCheeseList(){
            cheeseList.clear();
            cheeseList.addAll(original);
            notifyDataSetChanged();
        }

        public void filterCheeseList(CharSequence filter){
            cheeseList.clear();
            int count = 0;
            int trim = filter.length();
            for (String cheeses:original) {
                if(cheeses.toLowerCase().startsWith(filter.toString().toLowerCase())){
                    cheeseList.add(cheeses);
                    Log.d(TAG, "filterCheeseList: Taco");
                    count++;
                }
                else if(cheeses.toLowerCase().substring(0,trim).compareTo(filter.toString().toLowerCase()) > 0){
                    break;
                }
                else if(count == 0){
                    continue;
                }
                else {
                    break;
                }
            }
            notifyDataSetChanged();
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
