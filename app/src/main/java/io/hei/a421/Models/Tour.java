package io.hei.a421.Models;

public class Tour {

    //Constructeur du tour

    private int scoreGagnant;
    private int nbLances;
    private int nbJoueurs;
    private int idPremierJoueur;

    public Tour(int scoreGagnant,int nbLances,int nbJoueurs, int idPremierJoueur){
        this.scoreGagnant=scoreGagnant;
        this.nbLances=nbLances;
        this.nbJoueurs=nbJoueurs;
        this.idPremierJoueur=idPremierJoueur;
    }

    public int getScoreGagnant(){ return scoreGagnant;}
    public int getNbLances(){ return nbLances;}
    public int getNbJoueurs(){ return nbJoueurs;}
    public int getIdPremierJoueur(){ return idPremierJoueur;}

}
