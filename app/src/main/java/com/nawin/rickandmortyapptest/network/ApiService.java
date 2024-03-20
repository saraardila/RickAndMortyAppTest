package com.nawin.rickandmortyapptest.network;
import com.nawin.rickandmortyapptest.models.Character;
import com.nawin.rickandmortyapptest.models.CharacterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //Descargar la informacion de la primera pagina de characters

    @GET("/api/character")
    Call<CharacterResponse> getCharacters();

    //Descargar la informacion de un personaje en concreto segun su ID

    @GET("/api/character/{idCharacter}")
    Call<Character> getCharacter(@Path("idCharacter") int id);

    //Descargar la informacion de otra pagina que no sea la primera

    @GET("/api/character?")
    Call<CharacterResponse> getPage(@Query("page") int pagina);
}

