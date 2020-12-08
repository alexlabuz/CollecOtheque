package com.alexlbz.collecothque.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.alexlbz.collecothque.Model.Network.SingleRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

abstract public class RequestDatabase {

    private SingleRequestQueue requestQueue;

    /**
     * Constructeur de classe : récupère l'instance du RequestQueue
     * @param context context de l'activité
     */
    public RequestDatabase(Context context) {
        this.requestQueue = SingleRequestQueue.getInstance(context);
    }

    /**
     * Télécharge des données au format JSON
     * @param url url de téléchargement
     * @param id identifiant de la requête afin de pouvoir récupèrer les données dans l'activité
     */
    public void recupJson(String url, final Integer id){
        // En cas de réussite
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                resultRequest(response, id);
            }
        };

        // En cas d'erreur
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorRequest(error, id);
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        requestQueue.getRequestQueue().add(request);
    }

    public void recupImage(String url, final Integer id){
        // En cas de réussite
        Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                resultRequest(response, id);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorRequest(error, id);
            }
        };

        ImageRequest request = new ImageRequest(url, listener, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, errorListener);
        requestQueue.getRequestQueue().add(request);
    }

    abstract public void resultRequest(Object o, Integer requestId);
    abstract public void errorRequest(VolleyError error, Integer requestId);

}
