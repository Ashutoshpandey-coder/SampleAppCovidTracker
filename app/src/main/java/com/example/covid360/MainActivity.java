package com.example.covid360;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid360.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Model> modelList = new ArrayList<>();
    private Model model;
    private CovidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Covid Updates");
        fetchDataAllOverIndia();
        fetchDataAsPerState();

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void fetchDataAllOverIndia(){
        String url = "https://corona.lmao.ninja/v2/countries/india/";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    binding.confirmedCases.setText(jsonObject.getString("cases"));
                    binding.activeCases.setText(jsonObject.getString("active"));
                    binding.recoveredCases.setText(jsonObject.getString("recovered"));
                    binding.deaths.setText(jsonObject.getString("deaths"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void fetchDataAsPerState(){
        String url = "https://api.covid19india.org/data.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray statewise = object.getJSONArray("statewise");

                    for (int i= 1; i < statewise.length(); i++){
                        JSONObject jsonObject = statewise.getJSONObject(i);

                        String stateName = jsonObject.getString("state");
                        String activeCases = jsonObject.getString("active");
                        String deaths = jsonObject.getString("deaths");
                        String recoveredCases = jsonObject.getString("recovered");
                        String confirmedCases = jsonObject.getString("confirmed");

                        model = new Model(stateName,activeCases,deaths,confirmedCases,recoveredCases);
                        modelList.add(model);

                    }
                    adapter = new CovidAdapter(getApplicationContext(),modelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.rvStatesData.setLayoutManager(linearLayoutManager);
                    binding.rvStatesData.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}