package pe.pucp.dduu.tel306.lab4g3.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.pucp.dduu.tel306.lab4g3.Entities.Pregunta;
import pe.pucp.dduu.tel306.lab4g3.R;

public class PreguntaAdapter extends RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder> {


    private Pregunta[] preguntas;
    private Context contexto;


    public PreguntaAdapter(Pregunta[] preguntas, Context contexto) {
        this.preguntas = preguntas;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public PreguntaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(contexto).inflate(R.layout.item_rv, parent, false);
        PreguntaViewHolder preguntaViewHolder = new PreguntaViewHolder(itemview);
        return preguntaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreguntaAdapter.PreguntaViewHolder holder, int position) {
        holder.context = contexto;
        Pregunta pregunta = preguntas[position];
        String data = pregunta.getId() + "/" + pregunta.getQuestionText();
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return preguntas.length;
    }

    public static class PreguntaViewHolder extends RecyclerView.ViewHolder {

        public Context context;
        public TextView textView;

        public PreguntaViewHolder(View itemview) {
            super(itemview);
            this.textView = itemview.findViewById(R.id.textView2);

        }


    }
}
