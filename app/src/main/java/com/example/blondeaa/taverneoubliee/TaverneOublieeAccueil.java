package com.example.blondeaa.taverneoubliee;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by blondeaa on 22/03/18.
 */

public class TaverneOublieeAccueil extends AppCompatActivity {
    String user = "";
    final String URL = "http://5.135.124.212/v1/";
    String pseudo="";
    String id ="";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taverne_oubliee_accueil);
        user =getIntent().getStringExtra("user");
        //Log.e("debug boucle","1");



        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, (URL+"user/"+user),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject json = (JSONObject) parser.parse(response);
                            pseudo =json.get("pseudo").toString();
                            id = json.get("id").toString();
                            TextView tv = (TextView)findViewById(R.id.pseudo);
                            tv.setText(tv.getText()+" "+pseudo);
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

        mRequestQueue.add(stringRequest);
        BottomNavigationView bottomNavView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment =null;
                switch (item.getItemId()){
                    case R.id.allTable :
                        selectedFragment = FragAll.newInstance();
                        break;
                    case R.id.userTable :
                        selectedFragment = FragUser.newInstance(id);
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.parent, selectedFragment);
                transaction.commit();
                return true;
            }
        });


        getSupportFragmentManager().beginTransaction().add(R.id.parent,new FragAll()).commit();


     }

}
