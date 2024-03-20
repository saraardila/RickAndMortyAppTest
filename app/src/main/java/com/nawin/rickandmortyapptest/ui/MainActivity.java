package com.nawin.rickandmortyapptest.ui;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nawin.rickandmortyapptest.R;
import com.nawin.rickandmortyapptest.adapters.CharacterAdapter;
import com.nawin.rickandmortyapptest.models.Character;
import com.nawin.rickandmortyapptest.models.CharacterResponse;
import com.nawin.rickandmortyapptest.network.ApiService;
import com.nawin.rickandmortyapptest.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    // Lista de personajes
    private ArrayList<Character> characters;


    // Adaptador del RecyclerView
    private CharacterAdapter adapter;

    // LayoutManager del RecyclerView
    private RecyclerView.LayoutManager RecyclerView;

    // Servicio de la API
    private ApiService apiService;

    //CharacterResponse para almacenar la respuesta y poder acceder a la siguiente pagina
    private CharacterResponse respuesta;

    //Buscador
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la lista de personajes
        characters = new ArrayList<>();

        // Inicializar el RecyclerView y su adaptador
        adapter = new CharacterAdapter(this, characters, R.layout.character_view_holder);
        RecyclerView recyclerView = findViewById(R.id.contenedorMain);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // Inicializar Retrofit y el servicio de la API
        Retrofit retrofit = RetrofitClient.getConexion();
        apiService = retrofit.create(ApiService.class);

        // Cargar los datos iniciales
        cargarDatos();
    }


    private void cargarDatos() {

        Call<CharacterResponse> doGetCarga = apiService.getCharacters();

        doGetCarga.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK
                        && response.body() != null){
                    respuesta = response.body();
                    characters.addAll(response.body().getResults());
                    adapter.notifyItemRangeInserted(0, characters.size());
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR AL CARGAR ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}