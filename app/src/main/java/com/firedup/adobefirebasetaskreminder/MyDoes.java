package com.firedup.adobefirebasetaskreminder;

public class MyDoes {
    String titledoes;
    String datedoes;
    String descdoes;
    String timedoes;
    String email;

    public MyDoes(String titledoes, String datedoes, String descdoes, String timedoes, String email, String keydoes) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;
        this.timedoes = timedoes;
        this.email = email;
        this.keydoes = keydoes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MyDoes(String titledoes, String datedoes, String descdoes, String timedoes, String keydoes) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;
        this.timedoes = timedoes;
        this.keydoes = keydoes;
    }

    public String getTimedoes() {
        return timedoes;
    }

    public void setTimedoes(String timedoes) {
        this.timedoes = timedoes;
    }

    public String getKeydoes() {
        return keydoes;
    }

    public void setKeydoes(String keydoes) {
        this.keydoes = keydoes;
    }

    String keydoes;
    public MyDoes() {
    }



    public String getTitledoes() {
        return titledoes;
    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }

    public String getDatedoes() {
        return datedoes;
    }

    public void setDatedoes(String datedoes) {
        this.datedoes = datedoes;
    }

    public String getDescdoes() {
        return descdoes;
    }

    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }
}
