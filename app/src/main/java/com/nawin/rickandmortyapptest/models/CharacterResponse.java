package com.nawin.rickandmortyapptest.models;

import java.util.List;


public class CharacterResponse {
    private List<Character> results;
    private Info info;

    public void setResults(List<Character> results){
        this.results = results;
    }

    public List<Character> getResults(){
        return results;
    }

    public void setInfo(Info info){
        this.info = info;
    }

    public Info getInfo(){
        return info;
    }

    @Override
    public String toString(){
        return
                "Respuesta{" +
                        "results = '" + results + '\'' +
                        ",info = '" + info + '\'' +
                        "}";
    }
}