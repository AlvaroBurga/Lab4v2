package pe.pucp.dduu.tel306.lab4g3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

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

import pe.pucp.dduu.tel306.lab4g3.Entities.Answer;
import pe.pucp.dduu.tel306.lab4g3.Entities.AnswerStats;
import pe.pucp.dduu.tel306.lab4g3.Entities.Stats;
import pe.pucp.dduu.tel306.lab4g3.Entities.Usuario;

public class PreguntasActivity extends AppCompatActivity {
    Usuario usuario;
    Gson gson = new Gson();
    int idPreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        //Validar JSON
        Boolean encontroArchivo;
        try(FileInputStream fileInputStream = openFileInput("registro.json");
            FileReader fileReader = new FileReader(fileInputStream.getFD());
            BufferedReader bufferedReader = new BufferedReader(fileReader); )
        {
            String json = bufferedReader.readLine();
            usuario =  gson.fromJson(json,Usuario.class);
            encontroArchivo=true;

        } catch (IOException e) {
            e.printStackTrace();
            encontroArchivo=false;
        }

        if (encontroArchivo)
        {
            //Ver si ya respondio pregunta
            if(tengoInternet())
            {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String url = "http://34.236.191.118:3000/api/v1/answers/detail?questionid="+idPreg+"&userid="+usuario.getCorreo(); //Se considera que el correo es el id
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                Boolean respuesta = Boolean.parseBoolean(response);
                                if(respuesta)
                                {
                                    //Presentar estadisticas
                                    estadisticas(idPreg);
                                }
                                else
                                {
                                    //Dar opciones
                                    opciones(idPreg);
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                requestQueue.add(stringRequest);
            }
        }
        else
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void estadisticas(int preg)
    {
        if(tengoInternet())
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://34.236.191.118:3000/api/v1/answers/stats?questionid="+preg; //Se considera que el correo es el id
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Stats stats = gson.fromJson(response,Stats.class);
                            int respuestas=0;
                            for (AnswerStats i:stats.getAnswerstats())
                            {
                                respuestas=+i.getCount();
                            }
                            if (respuestas==0)
                            {
                                //indicar que no hay respuestas en el fragment
                            }
                            else
                            {
                                //mostrar estadisticas
                                for (AnswerStats i:stats.getAnswerstats())
                                {
                                    String texto = i.getAnswer().getAnswerText();
                                    double porcentaje = i.getCount()/respuestas * 100;
                                    //Hacer el recycler view en el fragment
                                }
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            requestQueue.add(stringRequest);
        }
    }

    public void opciones(int preg)
    {
        if(tengoInternet())
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://34.236.191.118:3000/api/v1/questions/"+preg; //Se considera que el correo es el id
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            //Permitir que se seleccione en el fragment

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            requestQueue.add(stringRequest);
        }
    }

    public Answer[] devolverOpciones(int preg)
    {
        if(tengoInternet())
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://34.236.191.118:3000/api/v1/questions/"+preg; //Se considera que el correo es el id
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {



                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            requestQueue.add(stringRequest);
        }
        return null;
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