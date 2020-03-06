package io.hei.a421.Models;

public class Joueur {

    //Constructeur du joueur

    private int idJoueur;
    private String pseudo;
    private int nbJetons;

    public Joueur(int idJoueur,String pseudo,int nbJetons){
        this.idJoueur=idJoueur;
        this.nbJetons=nbJetons;
        this.pseudo=pseudo;
    }

    public int getIdJoueur(){ return idJoueur;}
    public String getPseudo(){ return pseudo;}
    public int getNbJetons(){ return nbJetons;}

}