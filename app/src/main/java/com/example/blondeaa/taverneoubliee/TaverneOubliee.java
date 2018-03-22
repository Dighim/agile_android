package com.example.blondeaa.taverneoubliee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class TaverneOubliee extends AppCompatActivity {
    private static final String URL = "5.135.124.212/";
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TaverneApp app = (TaverneApp) getApplication();
        mRequestQueue = app.getVolleyRequestQueue();
        setContentView(R.layout.activity_taverne_oubliee);


    }




    public void onConnexion(View v){
        Intent intent = new Intent(TaverneOubliee.this, TaverneOublieeAccueil.class);
        JsonArrayRequest request = new JsonArrayRequest(URL,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // Ce code est appelé quand la requête réussi. Étant ici dans le thread principal, on va pouvoir mettre à jour notre Adapter
                mAdapter.updateMembers(jsonArray);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Le code suivant est appelé lorsque Volley n'a pas réussi à récupérer le résultat de la requête
                Toast.makeText(GithubListActivity.this, "Error while getting JSON: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        startActivity(intent);

    }
}
