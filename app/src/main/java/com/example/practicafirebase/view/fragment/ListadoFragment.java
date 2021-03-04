package com.example.practicafirebase.view.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.practicafirebase.R;
import com.example.practicafirebase.model.pojo.Consola;
import com.example.practicafirebase.view.adapter.RecyclerAdapter;
import com.example.practicafirebase.viewmodel.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ListadoFragment extends Fragment {
    ViewModel viewModel;
    TextView saludotv;
    FloatingActionButton fabadd, fabvolver;
    RecyclerView recyclerView;
    Dialog dialog;
    NavController navController;
    public ListadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(getView());
        saludotv = view.findViewById(R.id.saludotv);
        saludotv.setText("Bienvenido "+ viewModel.getCurrentuser().getEmail());
        fabadd = view.findViewById(R.id.fabadd);
        fabvolver = view.findViewById(R.id.fabvolver);
        List<Consola> list = new ArrayList<>();
        RecyclerAdapter adapter = new RecyclerAdapter(list, getContext());
        recyclerView = view.findViewById(R.id.recyclerconsolas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        viewModel.getConsolas();
        viewModel.getConsolaLiveData().observeForever(new Observer<List<Consola>>() {
            @Override
            public void onChanged(List<Consola> consolas) {
                list.clear();
                list.addAll(consolas);
                adapter.notifyDataSetChanged();
                Log.v("xyz",list.toString());
            }
        });

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_consola);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);

                EditText etNombre = dialog.findViewById(R.id.nombreconsolaet);
                EditText etEstado = dialog.findViewById(R.id.estadoconsolaet);
                EditText etPrecio = dialog.findViewById(R.id.precioconsolaet);
                Button btadd = dialog.findViewById(R.id.btAdd);
                btadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombre = etNombre.getText().toString();
                        String estado = etEstado.getText().toString();
                        double precio = Double.parseDouble(etPrecio.getText().toString());
                        Consola consola = new Consola(nombre,estado,precio);
                        viewModel.añadirConsola(consola);
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        fabvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_listadoFragment_to_loginFragment);
            }
        });
    }
}