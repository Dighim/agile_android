package com.example.blondeaa.taverneoubliee;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/


/**
 * A simple {@link Fragment} subclass.
 */
public class FragAll extends Fragment {
    private final String URL = "http://5.135.124.212/v1/table";
    private ArrayList<String> listItem = new ArrayList<String>();
    private ListView list = null;
    private LinearLayout linearLayout;


    public static FragAll newInstance(){
        FragAll fragment = new FragAll();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("Creation adapter", String.valueOf(adapter!=null));



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewCreate = inflater.inflate(R.layout.fragment_frag_all, container, false);
        list = (ListView)viewCreate.findViewById(R.id.listAll);

        Log.e("TOZ CREATE VIEW",String.valueOf(list !=null));
        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        StringRequest requestTables = new StringRequest(Request.Method.GET, (URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray tab = new JSONArray(response);
                            for(int i = 0 ; i < tab.length();i++){
                                JSONObject obj = tab.getJSONObject(i);
                                String name = obj.getString("intitule");
                                listItem.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("Test profil existant","Table");
                        Log.e("Reponse table", response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Test table existant","Non " + (URL));
                    }
                });
        mRequestQueue.add(requestTables);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),R.layout.simple_list_view,listItem);
        list.setAdapter(adapter);
        return viewCreate;

    }





}
