package pe.pucp.dduu.tel306.lab4g3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.pucp.dduu.tel306.lab4g3.Adapters.EstadisticasAdapter;
import pe.pucp.dduu.tel306.lab4g3.Entities.Stats;


public class EstadisticasFragment extends Fragment {

    private Stats stats;
    private double[] estadisticas;

    public EstadisticasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EstadisticasFragment newInstance(Stats stats, double[] estadisticas) {
        EstadisticasFragment fragment = new EstadisticasFragment();
        Bundle args = new Bundle();
        args.putSerializable("stats",stats);
        args.putDoubleArray("estadisticas",estadisticas);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stats=(Stats) getArguments().getSerializable("stats");
        estadisticas = getArguments().getDoubleArray("estadisticas");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_respuestas, container, false);
        //Se pone el titulo
        TextView titulo = view.findViewById(R.id.TituloStat);
        titulo.setText(stats.getQuestionText());
        //Se pone el recycler view
        EstadisticasAdapter estadisticasAdapter = new EstadisticasAdapter(stats.getAnswerstats(),estadisticas,getActivity());
        RecyclerView rv = view.findViewById(R.id.rvStats);
        rv.setAdapter(estadisticasAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}