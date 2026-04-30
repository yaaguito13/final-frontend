package com.example.moda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moda.R;
import com.example.moda.models.Direccion;

import java.util.List;

public class DireccionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DIRECCION = 0;
    private static final int TYPE_ADD = 1;
    private static final int TYPE_FORM = 2;

    private List<Direccion> direccionList;
    private Context context;
    private boolean isAddingNew = false;
    private int selectedPosition = 0; // First item selected by default
    private OnDireccionAction listener;

    public interface OnDireccionAction {
        void onGuardar(String titulo, String direccionCompleta);
    }

    public DireccionAdapter(Context context, List<Direccion> direccionList, OnDireccionAction listener) {
        this.context = context;
        this.direccionList = direccionList;
        this.listener = listener;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direccionList = direcciones;
        this.isAddingNew = false;
        this.selectedPosition = 0; // Reset selection when list updates
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (direccionList != null && position == direccionList.size()) {
            return isAddingNew ? TYPE_FORM : TYPE_ADD;
        }
        return TYPE_DIRECCION;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_direccion, parent, false);
            return new AddViewHolder(view);
        } else if (viewType == TYPE_FORM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_form_direccion, parent, false);
            return new FormViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_direccion, parent, false);
            return new DireccionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_DIRECCION) {
            DireccionViewHolder dirHolder = (DireccionViewHolder) holder;
            Direccion dir = direccionList.get(position);

            dirHolder.tvDireccionTitulo.setText(dir.getTitulo());
            
            // Format address neatly, omitting empty fields
            String addressText = dir.getCalle();
            if (dir.getCodigoPostal() != null && !dir.getCodigoPostal().isEmpty()) {
                addressText += ", " + dir.getCodigoPostal();
            }
            if (dir.getCiudad() != null && !dir.getCiudad().isEmpty()) {
                addressText += " " + dir.getCiudad();
            }
            dirHolder.tvDireccionCompleta.setText(addressText);

            // Principal pill logic
            if (position == 0) {
                dirHolder.tvPrincipalPill.setVisibility(View.VISIBLE);
            } else {
                dirHolder.tvPrincipalPill.setVisibility(View.GONE);
            }

            // Selection logic
            if (position == selectedPosition) {
                dirHolder.itemView.setBackgroundResource(R.drawable.bg_card_border);
                dirHolder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                dirHolder.itemView.setBackgroundResource(R.drawable.bg_card_border_unselected);
                dirHolder.ivCheck.setVisibility(View.GONE);
            }

            // Click listener for selection
            dirHolder.itemView.setOnClickListener(v -> {
                int prev = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(prev);
                notifyItemChanged(selectedPosition);
            });

            dirHolder.ivDelete.setOnClickListener(v -> {
                Toast.makeText(context, "Borrar " + dir.getTitulo() + " (No implementado en servidor aún)", Toast.LENGTH_SHORT).show();
            });

        } else if (holder.getItemViewType() == TYPE_ADD) {
            holder.itemView.setOnClickListener(v -> {
                isAddingNew = true;
                notifyItemChanged(position);
            });
        } else if (holder.getItemViewType() == TYPE_FORM) {
            FormViewHolder formHolder = (FormViewHolder) holder;
            
            formHolder.btnFormCancelar.setOnClickListener(v -> {
                isAddingNew = false;
                notifyItemChanged(position);
            });
            
            formHolder.btnFormGuardar.setOnClickListener(v -> {
                String titulo = formHolder.etFormEtiqueta.getText().toString().trim();
                String direccionCompleta = formHolder.etFormDireccion.getText().toString().trim();
                
                if (titulo.isEmpty() || direccionCompleta.isEmpty()) {
                    Toast.makeText(context, "Completa ambos campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                if (listener != null) {
                    listener.onGuardar(titulo, direccionCompleta);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (direccionList != null ? direccionList.size() : 0) + 1; 
    }

    public static class DireccionViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccionTitulo, tvPrincipalPill, tvDireccionCompleta;
        ImageView ivCheck, ivDelete;

        public DireccionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccionTitulo = itemView.findViewById(R.id.tvDireccionTitulo);
            tvPrincipalPill = itemView.findViewById(R.id.tvPrincipalPill);
            tvDireccionCompleta = itemView.findViewById(R.id.tvDireccionCompleta);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

    public static class AddViewHolder extends RecyclerView.ViewHolder {
        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class FormViewHolder extends RecyclerView.ViewHolder {
        EditText etFormEtiqueta, etFormDireccion;
        Button btnFormCancelar, btnFormGuardar;

        public FormViewHolder(@NonNull View itemView) {
            super(itemView);
            etFormEtiqueta = itemView.findViewById(R.id.etFormEtiqueta);
            etFormDireccion = itemView.findViewById(R.id.etFormDireccion);
            btnFormCancelar = itemView.findViewById(R.id.btnFormCancelar);
            btnFormGuardar = itemView.findViewById(R.id.btnFormGuardar);
        }
    }
}
