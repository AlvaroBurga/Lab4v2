package pe.pucp.dduu.tel306.lab4g3;

import android.os.Bundle;

        import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

import pe.pucp.dduu.tel306.lab4g3.Adapters.PreguntaAdapter;
import pe.pucp.dduu.tel306.lab4g3.Entities.Pregunta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreguntaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreguntaFragment extends Fragment {

    private Pregunta[] listaPreguntas;


    public PreguntaFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PreguntaFragment newInstance(Pregunta[] listaPreguntas) {
        PreguntaFragment fragment = new PreguntaFragment();
        Bundle args = new Bundle();
        args.putSerializable("listaPreguntas", listaPreguntas);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaPreguntas = (Pregunta[]) getArguments().getSerializable("listaPreguntas");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pregunta, container, false);


        PreguntaAdapter adapter = new PreguntaAdapter(listaPreguntas, getActivity().getApplicationContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        return view;
}
}