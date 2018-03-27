package com.example.blondeaa.taverneoubliee;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class TaverneOubliee extends AppCompatActivity {
    private static final String URL = "http://5.135.124.212/v1/user";
    private String user = "NOPE";
    private boolean auth=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_taverne_oubliee);


    }




    public void onConnexion(View v){
        final Intent intent = new Intent(TaverneOubliee.this, TaverneOublieeAccueil.class);
        auth =false;
        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        EditText username = findViewById(R.id.username);
                        user =username.getText().toString();
                        /*TextView tv = (TextView) findViewById(R.id.titreCo);
                        tv.setText(user);*/
                        auth=true;
                        Log.e("Connexion","Success");

                        intent.putExtra("user", user);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Context ctx = getApplicationContext();
                        String errorMsg = "Connection Failed";
                        Toast.makeText(ctx,errorMsg,Toast.LENGTH_LONG).show();
                        Log.e("Connexion",error.toString());
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                EditText username = (EditText) findViewById(R.id.username);
                EditText mdp = (EditText) findViewById(R.id.password);

                headers.put("Authorization", "Basic " + Base64.encodeToString((username.getText().toString()+":"+mdp.getText().toString()).getBytes(),Base64.DEFAULT));
                return headers;
            }
        };

        mRequestQueue.add(stringRequest);
        /*Log.e("Verif Bool", String.valueOf(auth));
        if(auth==true) {
            intent.putExtra("User", user);
            startActivity(intent);
        }*/

    }
}
