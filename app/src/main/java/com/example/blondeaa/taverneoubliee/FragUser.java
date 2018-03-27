package com.example.blondeaa.taverneoubliee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragUser extends ListFragment {
    private final String URL = "http://5.135.124.212/v1/table";
    private ArrayList<String> listItem = new ArrayList<String>();
    ListView list;
    String id;



    public static FragUser newInstance(String id){
        FragUser fragment = new FragUser();
        fragment.setId(id);
        Log.e("instance de fragAll","ok");
        return fragment;
    }

    public void setId(String id){
        this.id=id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("boucle","ext");
        View view = inflater.inflate(R.layout.fragment_frag_user, container, false);
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

                                if(obj.getString("crea").equals(id)) {
                                    String name = obj.getString("intitule");

                                    String message;
                                    String date = obj.getString("date");
                                    date = date.substring(0,10);
                                    String heure = obj.getString("date");
                                    heure = heure.substring(11,16);
                                    String lieu = obj.getString("lieu");
                                    message = name +" le " +date+" à "+heure+ "à "+lieu;
                                    listItem.add(message);
                                }
                            }
                            ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listItem);
                            setListAdapter(adapter);

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
        Log.e("tsdndlfdfp",String.valueOf(listItem.size()));

    }

}
