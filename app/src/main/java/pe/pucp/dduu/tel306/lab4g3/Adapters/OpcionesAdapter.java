package pe.pucp.dduu.tel306.lab4g3.Adapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import pe.pucp.dduu.tel306.lab4g3.Entities.Answer;
import pe.pucp.dduu.tel306.lab4g3.R;

public class OpcionesAdapter extends RecyclerView.Adapter<OpcionesAdapter.ViewHolder> {

    Answer[] opciones;
    Context context;
    int idPreg;
    int idUser;

    public OpcionesAdapter(Answer[] opciones, Context context, int idPreg, int idUser)
    {
        this.opciones=opciones;
        this.context=context;
        this.idPreg=idPreg;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.opcion,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.opcion = opciones[position];
        holder.context=context;
        holder.idPreg=idPreg;
        holder.idUser=idUser;

    }

    @Override
    public int getItemCount() {
        return opciones.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        Answer opcion;
        Context context;
        int idPreg;
        int idUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView op = itemView.findViewById(R.id.textoOpcion);
            Button votar = itemView.findViewById(R.id.Votar);


            op.setText(opcion.getAnswerText());
            //Cuando se presiona una opcion, se manda al post
            votar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(tengoInternet())
                    {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        String url = "http://34.236.191.118:3000/api/v1/questions/"+idPreg+"/answer";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response)
                                    {
                                        Log.d("TAG", "onResponse: "+ response);
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                }
                        )
                        {
                            //Se mandan los parametros necesarios por el body
                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                Map<String,String> param = new HashMap<>();
                                param.put("iduser",String.valueOf(idUser));
                                param.put("idanswer",String.valueOf(opcion.getId()));
                                Gson gson = new Gson();
                                String json = gson.toJson(param);
                                return json.getBytes();
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            });
        }

        public boolean tengoInternet() {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

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
}
