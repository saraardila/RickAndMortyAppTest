package com.nawin.rickandmortyapptest.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.nawin.rickandmortyapptest.R;
import com.nawin.rickandmortyapptest.models.Character;
import com.nawin.rickandmortyapptest.ui.CharacterDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterVH> implements Filterable {
    private Context context;
    private List<Character> objects;

    private List<Character> charactersFull;
    private int resource;

    public CharacterAdapter(Context context, List<Character> objects, int resource) {
        this.context = context;
        this.objects = objects;
        this.resource = resource;
        this.charactersFull = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public CharacterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View characterView = LayoutInflater.from(context).inflate(resource, null);
        characterView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new CharacterVH(characterView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterVH holder, int position) {

        Character character = objects.get(position);

        holder.lbName.setText(character.getName());

        // Setear el estado y el color del texto
        holder.tvAlive.setText(character.getStatus());
        if (character.getStatus().equalsIgnoreCase("alive")) {
            holder.tvAlive.setTextColor(ContextCompat.getColor(context, R.color.alive));
        } else if (character.getStatus().equalsIgnoreCase("dead")){
            holder.tvAlive.setTextColor(ContextCompat.getColor(context, R.color.dead));
        }else {
            holder.tvAlive.setTextColor(ContextCompat.getColor(context, R.color.unknown));
        }
        Picasso.get()
                .load(character.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgCharacter);

        //Al hacer click en un item,abrir y pasar el ID de ese item a una nueva actividad
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CharacterDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", character.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class CharacterVH extends RecyclerView.ViewHolder{

        ImageView imgCharacter;
        TextView lbName;

        TextView tvAlive;
        public CharacterVH(@NonNull View itemView) {
            super(itemView);

            imgCharacter = itemView.findViewById(R.id.imgCharacterCard);
            lbName = itemView.findViewById(R.id.lbNameCharacterCard);
            tvAlive = itemView.findViewById(R.id.tvAlive);
        }
    }

    @Override
    public Filter getFilter() {
        return characterFilter;
    }
    private Filter characterFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Character> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(objects);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Character character : objects) {
                    if (character.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(character);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<Character> filteredList = (List<Character>) filterResults.values;

            // Si el texto de búsqueda está vacío, mostrar la lista original
            if (charSequence == null || charSequence.length() == 0) {
                objects.clear();
                if (charactersFull != null) { // Asegurar que charactersFull no sea nulo
                    objects.addAll(charactersFull); // Restaurar la lista original
                }
            } else {
                // Si hay un texto de búsqueda, mostrar los resultados filtrados
                objects.clear();
                objects.addAll(filteredList);
            }

            // Notificar al RecyclerView que los datos han cambiado
            notifyDataSetChanged();
        }


    };
}


