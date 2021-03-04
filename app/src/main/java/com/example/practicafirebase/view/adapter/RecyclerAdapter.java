package com.example.practicafirebase.view.adapter;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicafirebase.R;
import com.example.practicafirebase.model.pojo.Consola;
import com.example.practicafirebase.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Consola> consolaArrayList;
    Context context;
    View view;
    ViewModel viewModel;



    public RecyclerAdapter(List<Consola> consolaArrayList, Context context) {
        this.consolaArrayList = consolaArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNombre.setText("Consola: "+consolaArrayList.get(position).getNombre());
        holder.tvEstado.setText("Estado: "+consolaArrayList.get(position).getEstado());
        holder.tvPrecio.setText("Precio: "+consolaArrayList.get(position).getPrecio());
        viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ViewModel.class);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewModel.currentConsola = consolaArrayList.get(position);
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_consola);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);

                EditText etNombre = dialog.findViewById(R.id.eteditnombreconsola);
                EditText etEstado = dialog.findViewById(R.id.eteditestadoconsola);
                EditText etPrecio = dialog.findViewById(R.id.eteditprecioconsola);

                etNombre.setText(consolaArrayList.get(position).getNombre());
                etEstado.setText(consolaArrayList.get(position).getEstado());
                etPrecio.setText(""+consolaArrayList.get(position).getPrecio());


                Button btEditar = dialog.findViewById(R.id.editarbt);
                Button btBorrar = dialog.findViewById(R.id.borrarbt);

                btEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        consolaArrayList.get(position).setEstado(etEstado.getText().toString());
                        consolaArrayList.get(position).setPrecio(Double.parseDouble(etPrecio.getText().toString()));
                        consolaArrayList.get(position).setNombre(etNombre.getText().toString());
                        viewModel.editarConsola(consolaArrayList.get(position));
                        dialog.cancel();
                    }
                });

                btBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.borrarConsola(viewModel.currentConsola);
                        dialog.cancel();
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return consolaArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvEstado, tvNombre, tvPrecio;

        ConstraintLayout parent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEstado = itemView.findViewById(R.id.tvestadoconsola);
            tvNombre = itemView.findViewById(R.id.tvnombreconsola);
            tvPrecio = itemView.findViewById(R.id.tvprecioconsola);

            parent = itemView.findViewById(R.id.parentrecycler);
        }
    }
}
