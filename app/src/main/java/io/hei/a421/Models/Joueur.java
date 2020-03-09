package io.hei.a421.Models;

public class Joueur {

    //Constructeur du joueur

    private int idJoueur;
    private String pseudo;
    private int nbJetons;

    public Joueur(int _idJoueur,String _pseudo,int _nbJetons){
        this.idJoueur=_idJoueur;
        this.nbJetons=_nbJetons;
        this.pseudo=_pseudo;
    }

    public Joueur(int _idJoueur, String _pseudo){
        this.idJoueur=_idJoueur;
        this.pseudo=_pseudo;
    }

    public int getIdJoueur(){ return idJoueur;}
    public String getPseudo(){ return pseudo;}
    public int getNbJetons(){ return nbJetons;}

    public String toString(){return "Ce joueur a pour id : "+idJoueur+ " et pour pseudo : "+pseudo;}

}
