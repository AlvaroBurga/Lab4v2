package pe.pucp.dduu.tel306.lab4g3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import pe.pucp.dduu.tel306.lab4g3.Entities.Pregunta;
import pe.pucp.dduu.tel306.lab4g3.Entities.Usuario;

public class ListaPreguntaActiviy extends AppCompatActivity {
    Gson gson = new Gson();
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pregunta_activiy);

        //Validar JSON
        Boolean encontroArchivo;
        try (FileInputStream fileInputStream = openFileInput("registro.txt");
             FileReader fileReader = new FileReader(fileInputStream.getFD());
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String json = bufferedReader.readLine();
            usuario = gson.fromJson(json, Usuario.class);
            encontroArchivo = true;

        } catch (IOException e) {
            e.printStackTrace();
            encontroArchivo = false;
        }

        if (encontroArchivo) {
            //Ver si ya respondio pregunta
            Preguntas();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void Preguntas() {
        if (tengoInternet()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://34.236.191.118:3000/api/v1/questions";
            //Con esto se obtienen las preguntas
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("infoWS", response);
                            Gson gson = new Gson();
                            //Se obtienen la lista de preguntas
                            Pregunta[] listaPreguntas = gson.fromJson(response, Pregunta[].class);
                            //Se crea el fragmento
                            PreguntaFragment preguntasFragment = PreguntaFragment.newInstance(listaPreguntas);
                            FragmentManager supportFragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragmentContainery, preguntasFragment);
                            fragmentTransaction.commit();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            requestQueue.add(stringRequest);
        }
    }
    public void finLista(int id) {
        Intent intent = new Intent(getApplicationContext(),DetallesPreguntaActivity.class);
        intent.putExtra("idPreg",id);
        startActivity(intent);
    }
    public boolean tengoInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
            return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network networks = connectivityManager.getActiveNetwork();
            if (networks == null)
                return false;

            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(networks);
            if (networkCapabilities == null)
                return false;

            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true;
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return true;
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                return true;
            return false;
        } else {

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null)
                return false;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_ETHERNET)
                return true;
            return false;

        }
    }
}
