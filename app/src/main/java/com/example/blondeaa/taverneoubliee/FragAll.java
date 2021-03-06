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
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.List;

/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/


/**
 * A simple {@link Fragment} subclass.
 */
public class FragAll extends ListFragment {
    private final String URL = "http://5.135.124.212/v1/table";
    private ArrayList<String> listItem = new ArrayList<String>();
    private ListView list = null;

    private LinearLayout linearLayout;


    public static FragAll newInstance(){
        FragAll fragment = new FragAll();
        return fragment;
    }


    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("boucle","ext");
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("boucle","-1");
        super.onActivityCreated(savedInstanceState);
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Log.e("bouble","0");

        StringRequest requestTables = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("boucle","1");
                        try {
                            JSONArray tab = new JSONArray(response);
                            for(int i = 0 ; i < tab.length();i++){
                                JSONObject obj = tab.getJSONObject(i);
                                String name = obj.getString("intitule");
                                String date = obj.getString("date");
                                date = date.substring(0,10);
                                String heure = obj.getString("date");
                                heure = heure.substring(11,16);
                                String lieu = obj.getString("lieu");

                                String message = name +" le " +date+" à "+heure+ " à "+lieu;
                                listItem.add(message);
                            }
                            ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listItem);
                            setListAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Test table existant","Non " + (URL));
                    }
                });
        mRequestQueue.add(requestTables);

    }





}
