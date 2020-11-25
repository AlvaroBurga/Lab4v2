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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void abriRegistro(View view){
        Intent intent = new Intent(this , RegistroActivity.class );
        startActivity(intent);
    }
    public void validarUsuario(View view){
        if (tengoInternet()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://34.236.191.118:3000/api/v1/users/login";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject usuario = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_LONG).show();

                                // GUARDAR
                                try(FileOutputStream fileOutputStream = openFileOutput("archivoLOGIN.txt", Context.MODE_PRIVATE);
                                    FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());
                                ) {
                                    fileWriter.write(response);
                                    Log.d("infoApp","escritura exitosa");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_LONG).show();
                            }
                            Log.d("infoWS", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.d("error","AAAAAAAAAAAAAAAAA");
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    HashMap<String, String> parametros = new HashMap<>();

                    EditText editText = findViewById(R.id.password);
                    String password = editText.getText().toString();

                    editText = findViewById(R.id.username);
                    String email = editText.getText().toString();

                    parametros.put("password", password);
                    parametros.put("email", email);

                    String json = gson.toJson(parametros);
                    byte[] retorno = json.getBytes();
                    Log.d("JSON", json);

                    return retorno;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

    public void validarInternet(View view) {
       Toast.makeText(this,tengoInternet()?"Si tengo internet":"No tengo internet", Toast.LENGTH_SHORT).show();
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