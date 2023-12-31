package com.example.semana4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class ListaBancos extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bancos);


        Bundle bundle = getIntent().getExtras();
        Map<String, String> datos = new HashMap<>();
        datos.put("fuente", "1");

        String url = "https://api.uealecpeterson.net/public/productos/search";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(datos),
                this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + bundle.getString("TOKEN"));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    //@Override
   // public void processFinish(String result) throws JSONException {
        //TextView Informacion = (TextView)findViewById(R.id.informacion);
        //String lstBancos="";
        //JSONObject JSONOBJ = new  JSONObject(result);
        //JSONArray JSONlista = JSONOBJ.getJSONArray("productos");
        //for(int i=0; i< JSONlista.length();i++){
            //JSONObject producto= JSONlista.getJSONObject(i);
           // lstBancos = lstBancos +" ("+i+") "+producto.getString("barcode").toString()+" "+" "+" "+producto.getString("descripcion").toString()
            //        +producto.getString("costo").toString()+" "+" "+" "+producto.getString("impuesto").toString()+"\n";

        //}
        //Informacion.setText(lstBancos);

    //}

    @Override
    public void onErrorResponse(VolleyError error) {
        TextView Informacion = (TextView)findViewById(R.id.informacion);
        Informacion.setText("ERROR");
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<String> lstBancos = new ArrayList<String>();
        JSONArray productosArray = null;
        try {
            // Acceder a la matriz "productos" dentro del objeto JSON principal
            productosArray = response.getJSONArray("productos");

            for (int i = 0; i < productosArray.length(); i++) {
                JSONObject producto = productosArray.getJSONObject(i);
                lstBancos.add("("+i+")"+producto.getString("barcode").toString()+" "+" "+" "+producto.getString("descripcion").toString()
                        +producto.getString("costo").toString()+" "+" "+" "+producto.getString("impuesto").toString()+"\n");
            }

            TextView txtvolley = findViewById(R.id.informacion);
            txtvolley.setText(lstBancos.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}