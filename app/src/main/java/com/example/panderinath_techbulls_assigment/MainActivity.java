package com.example.panderinath_techbulls_assigment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_movies_list;
    private MoviesListAdapter adapter;
    Context mContext;
    EditText et_search;
    LinearLayoutManager layoutManager;
    ArrayList<MoviesModel> arrayList = new ArrayList<>();
    ArrayList<MoviesModel> arrayList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        et_search = findViewById(R.id.et_search);
        rv_movies_list = findViewById(R.id.rv_movies_list);
        rv_movies_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        rv_movies_list.setLayoutManager(layoutManager);
        rv_movies_list.setItemAnimator(new DefaultItemAnimator());
        adapter = new MoviesListAdapter(arrayList,mContext);
        rv_movies_list.setAdapter(adapter);
        getMoviesDetails();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    arrayList2.clear();
                 if(charSequence.length()>2) {
                     for (int j = 0; j < arrayList.size(); j++) {
                         if (arrayList.get(j).getName().contains(charSequence)) {
                         //if (charSequence.equals(arrayList.get(j).getName())) {
                             arrayList2.add(arrayList.get(j));
                             adapter = new MoviesListAdapter(arrayList2, MainActivity.this);
                             rv_movies_list.setAdapter(adapter);
                             adapter.notifyDataSetChanged();
                         }
                     }
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // API to Get the Movies Details
    public void getMoviesDetails(){
        //progressDialog.show();

        String targetUrlShopList = "http://www.omdbapi.com/?s=batman&apikey=a6b9a90f";

        Log.d("targetUrlDistance",targetUrlShopList);
        StringRequest orderListRequest = new StringRequest(Request.Method.GET,
                targetUrlShopList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API_RESPONSE",response);

                        //progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            JSONArray jsonArray = jsonObject.getJSONArray("Search");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject cartListObject = jsonArray.getJSONObject(i);
                                    MoviesModel model = new MoviesModel();
                                    model.setName(cartListObject.getString("Title"));
                                    model.setType(cartListObject.getString("Type"));
                                    model.setDate(cartListObject.getString("Year"));
                                    model.setPoster(cartListObject.getString("Poster"));
                                    arrayList.add(model);
                                }

                            adapter = new MoviesListAdapter(arrayList, mContext);
                            rv_movies_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                    }
                });
        MySingleton.getInstance(mContext).addToRequestQueue(orderListRequest);
    }
}