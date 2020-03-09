package io.hei.a421.Models;

public class Partie {

    //Constructeur de la partie

    private int nbJoueurs;
    private int idPremierJoueur;

    public Partie(int _nbJoueurs,int _idPremierJoueur){
        this.nbJoueurs=_nbJoueurs;
        this.idPremierJoueur=_idPremierJoueur;
    }

    public int getNbJoueurs(){ return nbJoueurs;}
    public int getIdPremierJoueur(){ return idPremierJoueur;}
}
