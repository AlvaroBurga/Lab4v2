package pe.pucp.dduu.tel306.lab4g3;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.pucp.dduu.tel306.lab4g3.Adapters.OpcionesAdapter;
import pe.pucp.dduu.tel306.lab4g3.Entities.Pregunta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpcionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class OpcionesFragment extends Fragment {


    private Pregunta pregunta;
    int idUser;


    public static OpcionesFragment newInstance(Pregunta pregunta, int idUser) {
        OpcionesFragment fragment = new OpcionesFragment();
        Bundle args = new Bundle();
        //inicializan los argumentos
        args.putSerializable("pregunta",pregunta);
        args.putInt("idUser",idUser);
        fragment.setArguments(args);
        return fragment;
    }

    public OpcionesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //se asignan a los atributos
            pregunta =(Pregunta) getArguments().getSerializable("pregunta");
            idUser = getArguments().getInt("idUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_opciones, container, false);
        //Se pone el titulo de la pregunta
        TextView titulo = view.findViewById(R.id.tituloPregunta);
        titulo.setText(pregunta.getQuestionText());
        //Se activa el recycler view
        OpcionesAdapter opcionesAdapter = new OpcionesAdapter(pregunta.getAnswers(), getActivity().getApplicationContext(), pregunta.getId(),idUser);
        RecyclerView rv = view.findViewById(R.id.opcionesRv);
        rv.setAdapter(opcionesAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}