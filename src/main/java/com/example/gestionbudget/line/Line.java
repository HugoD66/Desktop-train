package com.example.gestionbudget.line;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Line {
    private LocalDate periode;
    private Float total;
    private Float logement;
    private Float nourriture;
    private Float sorties;
    private Float transport;
    private Float voyage;
    private Float impots;
    private Float autres;

    public Line() {
        this( String.valueOf(LocalDate.now()), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    public Line(String periode, Float logement, Float nourriture, Float sorties, Float transport, Float voyage, Float impots, Float autres) {
        setPeriode(periode);
        this.total = logement + nourriture + sorties  + transport + voyage + impots + autres;
        this.logement = logement;
        this.nourriture = nourriture;
        this.sorties = sorties;
        this.transport = transport;
        this.voyage = voyage;
        this.impots = impots;
        this.autres = autres;
    }

    public String getPeriode() {
        return periode.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setPeriode(String periodeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.periode = LocalDate.parse(periodeString, formatter);
    }



    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getLogement() {
        return logement;
    }

    public void setLogement(Float logement) {
        this.logement = logement;
    }

    public Float getNourriture() {
        return nourriture;
    }

    public void setNourriture(Float nourriture) {
        this.nourriture = nourriture;
    }

    public Float getSorties() {
        return sorties;
    }

    public void setSorties(Float sorties) {
        this.sorties = sorties;
    }


    public Float getVoyage() {
        return voyage;
    }

    public void setVoyage(Float voyage) {
        this.voyage = voyage;
    }

    public Float getImpots() {
        return impots;
    }

    public void setImpots(Float impots) {
        this.impots = impots;
    }

    public Float getAutres() {
        return autres;
    }

    public void setAutres(Float autres) {
        this.autres = autres;
    }

    public Float getTransport() {
        return transport;
    }

    public void setTransport(Float transport) {
        this.transport = transport;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return "Line{" +
                "periode=" + (periode != null ? periode.format(formatter) : "null") +
                ", total=" + total +
                ", logement=" + logement +
                ", nourriture=" + nourriture +
                ", sorties=" + sorties +
                ", transport=" + transport +
                ", voyage=" + voyage +
                ", impots=" + impots +
                ", autres=" + autres +
                '}';
    }
}
