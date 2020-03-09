package io.hei.a421.Models;

public class Tour {

    //Constructeur du tour

    private int scoreGagnant;
    private int nbLances;
    private int nbJoueurs;
    private int idPremierJoueur;

    public Tour(int _scoreGagnant,int _nbLances,int _nbJoueurs, int _idPremierJoueur){
        this.scoreGagnant=_scoreGagnant;
        this.nbLances=_nbLances;
        this.nbJoueurs=_nbJoueurs;
        this.idPremierJoueur=_idPremierJoueur;
    }

    public int getScoreGagnant(){ return scoreGagnant;}
    public int getNbLances(){ return nbLances;}
    public int getNbJoueurs(){ return nbJoueurs;}
    public int getIdPremierJoueur(){ return idPremierJoueur;}

}
