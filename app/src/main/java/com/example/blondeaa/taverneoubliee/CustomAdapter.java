package com.example.blondeaa.taverneoubliee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by blondeaa on 22/03/18.
 */

public class CustomAdapter extends BaseAdapter {
    private final Context mContext;
    private JSONArray mMembers;


    public CustomAdapter(Context c){
        mContext =c;

    }

    @Override
    public int getCount() {
        return (mMembers == null) ? 0 : mMembers.length();
    }

    @Override
    public Object getItem(int i) {
        JSONObject item = null;
        if (mMembers != null) {
            try {
                item = mMembers.getJSONObject(i);
            } catch (JSONException e) {
                // loguer l'erreur
            }
        }
        return item;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        TextView login;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            login = (TextView) convertView.findViewById(R.id.login);
        } else {
            login = viewHolder.mLogin;

        }

        // On récupère les informations depuis le JSONObject et on les relie aux vues
        JSONObject json = (JSONObject) getItem(i);
        login.setText(json.optString("login"));
        return convertView;

        return null;
    }
}
