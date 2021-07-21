package com.example.projeto1.model;

public class DisciplinaModel {

    private int id;
    private String nome;
    private String color;

    public DisciplinaModel(int id, String nome, String color) {
        this.id = id;
        this.nome = nome;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
