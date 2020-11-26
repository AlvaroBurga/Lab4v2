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
        AnswerStats opcion=opciones[position];
        double porcentaje = porcentajes[position];
        holder.context=context;
        holder.opcionTv.setText(opcion.getAnswer().getAnswerText());
        String porcentajeS = String.valueOf(porcentaje)+ "%";
        holder.porcentajeTv.setText(porcentajeS);
    }

    @Override
    public int getItemCount() {
        return opciones.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        TextView opcionTv;
        TextView porcentajeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            opcionTv = itemView.findViewById(R.id.opcionStat);
            //Se asigna la opcion y su respectivo porcentaje a cada elemento del recycler view
            porcentajeTv = itemView.findViewById(R.id.porcentaje);
        }
    }
}
