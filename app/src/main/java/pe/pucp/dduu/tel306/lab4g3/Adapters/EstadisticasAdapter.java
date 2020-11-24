package pe.pucp.dduu.tel306.lab4g3.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.pucp.dduu.tel306.lab4g3.Entities.Answer;
import pe.pucp.dduu.tel306.lab4g3.Entities.AnswerStats;
import pe.pucp.dduu.tel306.lab4g3.R;

public class EstadisticasAdapter extends RecyclerView.Adapter<EstadisticasAdapter.ViewHolder> {

    AnswerStats[] opciones;
    double[] porcentajes;
    Context context;
    int idPreg;

    public EstadisticasAdapter(AnswerStats[] opciones, double[] porcentajes, Context context)
    {
        this.opciones=opciones;
        this.porcentajes=porcentajes;
        this.context = context;
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
        holder.opcion=opciones[position];
        holder.context=context;
        holder.porcentaje=porcentajes[position];
    }

    @Override
    public int getItemCount() {
        return opciones.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        AnswerStats opcion;
        Context context;
        double porcentaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView opcionTv = itemView.findViewById(R.id.opcionStat);
            //Se asigna la opcion y su respectivo porcentaje a cada elemento del recycler view
            TextView porcentajeTv = itemView.findViewById(R.id.porcentaje);
            opcionTv.setText(opcion.getAnswer().getAnswerText() + " -> ");
            porcentajeTv.setText(String.valueOf(porcentaje)+ "%");
        }
    }
}
