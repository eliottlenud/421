package io.hei.a421.Models;

public class Partie {

    //Constructeur de la partie

    private int nbJoueurs;
    private int idPremierJoueur;

    public Partie(int nbJoueurs,int idPremierJoueur){
        this.nbJoueurs=nbJoueurs;
        this.idPremierJoueur=idPremierJoueur;
    }

    public int getNbJoueurs(){ return nbJoueurs;}
    public int getIdPremierJoueur(){ return idPremierJoueur;}
}
