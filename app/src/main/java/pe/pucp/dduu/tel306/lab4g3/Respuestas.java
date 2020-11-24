package pe.pucp.dduu.tel306.lab4g3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Respuestas extends Fragment {

    private String mParam2;

    public Respuestas() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Respuestas newInstance() {
        Respuestas fragment = new Respuestas();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_respuestas, container, false);
    }
}