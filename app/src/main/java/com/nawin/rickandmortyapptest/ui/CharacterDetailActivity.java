package com.nawin.rickandmortyapptest.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nawin.rickandmortyapptest.R;
import com.nawin.rickandmortyapptest.models.Character;
import com.nawin.rickandmortyapptest.network.ApiService;
import com.nawin.rickandmortyapptest.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {

    private ApiService api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_detail); // Infla el layout de la actividad

        // Inicializar vistas
        ImageView imageView = findViewById(R.id.imgFotoVerCharacter);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView especieTextView = findViewById(R.id.speciesTextView);
        TextView generoTextView = findViewById(R.id.maleTextView);
        TextView statusTextView = findViewById(R.id.statusTextView);
        TextView tipoTextView = findViewById(R.id.typeRickTextView);
        TextView placeTextView = findViewById(R.id.locationTextView);
        TextView fisrtTextView = findViewById(R.id.originTextView);

        //Coger el ID que pasamos en el adapter
        if(getIntent().getExtras() != null){
            int id = getIntent().getExtras().getInt("ID");

            api = RetrofitClient.getConexion().create(ApiService.class);
            Call<Character> character = api.getCharacter(id);

            character.enqueue(new Callback<Character>() {
                @Override
                public void onResponse(Call<Character> call, Response<Character> response) {
                    if (response.code() == HttpsURLConnection.HTTP_OK
                            && response.body() != null){
                         Character c = response.body();
                        // Establecer los datos del personaje en las vistas
                        nameTextView.setText(c.getName());
                        especieTextView.setText(c.getSpecies());
                        generoTextView.setText(c.getGender());
                        statusTextView.setText(c.getStatus());
                        placeTextView.setText(c.getLocation().getName());
                        fisrtTextView.setText(c.getOrigin().getName());

                        String type = c.getType();
                        if (type != null && !type.isEmpty()) {
                            tipoTextView.setText(type);
                        } else {
                            tipoTextView.setText("Tipo desconocido");
                        }


                        // Cargar la imagen del personaje utilizando Picasso
                        Picasso.get()
                                .load(c.getImage())
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_foreground)
                                .into(imageView);
                    }else {
                        Toast.makeText(CharacterDetailActivity.this, "Failed to load character details", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Character> call, Throwable t) {
                    Toast.makeText(CharacterDetailActivity.this, "ERROR AL CARGAR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}