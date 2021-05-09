package com.example.covid360;

public class Model {
    private String state;
    private String active;
    private String deaths;
    private String confirmed;
    private String recovered;

    public Model(String state, String active, String deaths, String confirmed, String recovered) {
        this.state = state;
        this.active = active;
        this.deaths = deaths;
        this.confirmed = confirmed;
        this.recovered = recovered;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public String getActive() {
        return active;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getRecovered() {
        return recovered;
    }
}
