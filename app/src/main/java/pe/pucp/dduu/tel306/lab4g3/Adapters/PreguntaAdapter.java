package pe.pucp.dduu.tel306.lab4g3.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.pucp.dduu.tel306.lab4g3.Entities.Pregunta;
import pe.pucp.dduu.tel306.lab4g3.DetallesPreguntaActivity;
import pe.pucp.dduu.tel306.lab4g3.ListaPreguntaActiviy;
import pe.pucp.dduu.tel306.lab4g3.R;

public class PreguntaAdapter extends RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder> {


    private Pregunta[] preguntas;
    private Activity activity;


    public PreguntaAdapter(Pregunta[] preguntas, Activity activity) {
        this.preguntas = preguntas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PreguntaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.item_rv, parent, false);
        PreguntaViewHolder preguntaViewHolder = new PreguntaViewHolder(itemview);
        return preguntaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreguntaAdapter.PreguntaViewHolder holder, int position) {
        holder.activity = activity;
        Pregunta pregunta = preguntas[position];
    }

    @Override
    public int getItemCount() {
        return preguntas.length;
    }

    public static class PreguntaViewHolder extends RecyclerView.ViewHolder {

        public Activity activity;
        public Pregunta pregunta;

        public PreguntaViewHolder(View itemview) {
            super(itemview);
            TextView textView = itemview.findViewById(R.id.preguntaLista);
            Button seleccionar = itemview.findViewById(R.id.seleccionarPregunta);
            textView.setText(pregunta.getQuestionText());
            seleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = pregunta.getId();
                    //Cerrar el fragment y pasar el id
                    ListaPreguntaActiviy listaPreguntaActiviy = (ListaPreguntaActiviy) activity;
                    listaPreguntaActiviy.finLista(id);
                }
            });

        }


    }
}
