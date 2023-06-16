package com.example.semana4;

import static java.lang.invoke.VarHandle.AccessMode.GET;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import WebService.Asynchtask;
import WebService.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void BtIngresar (View view){
        EditText Nombre = findViewById(R.id.nombre);
        EditText Contraseña = findViewById(R.id.contraseña);
        String nombre;
        String  contraseña;
        nombre=Nombre.getText().toString();
        contraseña=Contraseña.getText().toString();
        //codigo para concetar a internet
        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://revistas.uteq.edu.ec/ws/login.php?usr="
                +nombre+"&pass="+contraseña,datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");

    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView respuesta =findViewById(R.id.respuesta);
        if (result.equals("Login Correcto!")){
            Intent intent = new Intent(MainActivity.this, ListaBancos.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}