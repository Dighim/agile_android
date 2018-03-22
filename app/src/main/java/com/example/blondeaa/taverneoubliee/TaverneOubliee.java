package com.example.blondeaa.taverneoubliee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;

public class TaverneOubliee extends AppCompatActivity {
    private static final String URL = "http://5.135.124.212/v1/user";
   // private RequestQueue mRequestQueue;
    private String user = "NOPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_taverne_oubliee);


    }




    public void onConnexion(View v){
        Intent intent = new Intent(TaverneOubliee.this, TaverneOublieeAccueil.class);

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView tv = (TextView) findViewById(R.id.titreCo);
                        //tv.setText(response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray();
                            Log.e("String to JSONObject", "Success : "+response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Connexion","Success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Connexion",error.toString());
                    }
                });

        mRequestQueue.add(stringRequest);

        intent.putExtra("Pseudo",user);
        //startActivity(intent);

    }
}
