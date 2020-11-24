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
import android.widget.Toast;

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

import pe.pucp.dduu.tel306.lab4g3.Entities.AnswerStats;
import pe.pucp.dduu.tel306.lab4g3.Entities.Pregunta;
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
                String url = "http://34.236.191.118:3000/api/v1/answers/detail?questionid="+idPreg+"&userid="+usuario.getId();
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
                                error.printStackTrace();
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
                            //Se obtienen las estadisticas
                            Stats stats = gson.fromJson(response,Stats.class);
                            int respuestas=0;
                            //Se cuenta el numero total de respuestas
                            for (AnswerStats i:stats.getAnswerstats())
                            {
                                respuestas=+i.getCount();
                            }
                            if (respuestas==0)
                            {
                                //No hay forma que se llegue si no se tiene respuestas
                                Toast.makeText(getApplicationContext(), "No debio llegar aqui",Toast.LENGTH_SHORT);
                            }
                            else {
                                //Primero se calculan las estadisticas por opcion
                                int l = stats.getAnswerstats().length;
                                double[] estadisticas = new double[l];
                                int cont=0;
                                for (AnswerStats i : stats.getAnswerstats())
                                {
                                    //Es la division entre el numero de cuentas de la opcion entre el total de respuestas
                                    estadisticas[cont]=(i.getCount()*1.0/respuestas);
                                    cont++;
                                }
                                //Se crea el fragment de estadisticas
                                EstadisticasFragment estadisticasFragment = EstadisticasFragment.newInstance(stats,estadisticas);
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.add(R.id.fragmentContainerx, estadisticasFragment);
                                fragmentTransaction.commit();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

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
            String url = "http://34.236.191.118:3000/api/v1/questions/"+preg;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            //Mostrarle el fragment de opciones con parametro de el arreglo de answers
                            Pregunta pregunta = gson.fromJson(response, Pregunta.class);
                            //Se crea el fragmento
                            OpcionesFragment opcionesFragment = OpcionesFragment.newInstance(pregunta,usuario.getId());
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.add(R.id.fragmentContainerx,opcionesFragment);
                            fragmentTransaction.commit();

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