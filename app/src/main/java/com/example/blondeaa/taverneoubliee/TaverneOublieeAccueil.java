package com.example.blondeaa.taverneoubliee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by blondeaa on 22/03/18.
 */

public class TaverneOublieeAccueil extends AppCompatActivity {
    String user = "";
    final String URL = "http://5.135.124.212/v1/user/";
    String pseudo="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taverne_oubliee_accueil);
        user =getIntent().getStringExtra("user");

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, (URL+user),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject json = (JSONObject) parser.parse(response);
                            pseudo =json.get("pseudo").toString();
                            TextView tv = (TextView)findViewById(R.id.pseudo);
                            tv .setText(tv.getText()+" "+pseudo);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Log.e("Test profil existant","Pseudo :"+ pseudo);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Test profil existant","Non " + (URL+user));
                    }
                });
        /*{

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                EditText username = (EditText) findViewById(R.id.username);
                EditText mdp = (EditText) findViewById(R.id.password);

                headers.put("Authorization", "Basic " + Base64.encodeToString((username.getText().toString()+":"+mdp.getText().toString()).getBytes(),Base64.DEFAULT));
                return headers;
            }
        };*/

        mRequestQueue.add(stringRequest);



    }

}
