package com.example.semana4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class ListaBancos extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bancos);


        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("fuente","1");
        WebService ws= new WebService(" https://api.uealecpeterson.net/public/clientes/search"
                ,datos, ListaBancos.this, ListaBancos.this);
        ws.execute("POST","Authorization","Bearer "+ bundle.getString("TOKEN"));

    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView Informacion = (TextView)findViewById(R.id.informacion);
        //Informacion.setText(result);
        String lstBancos="";
        JSONObject JSONOBJ = new  JSONObject(result);
        JSONArray JSONlista = JSONOBJ.getJSONArray("clientes");
        for(int i=0; i< JSONlista.length();i++){
            JSONObject cliente= JSONlista.getJSONObject(i);
            lstBancos = lstBancos +" "+i+" "+cliente.getString("nombre").toString()+cliente.getString("direccion").toString();

        }
        Informacion.setText(lstBancos);

    }
}