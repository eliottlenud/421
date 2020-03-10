package io.hei.a421.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Joueur implements Parcelable  {

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

    protected Joueur(Parcel in) {
        idJoueur = in.readInt();
        pseudo = in.readString();
        nbJetons = in.readInt();
    }

    public static final Creator<Joueur> CREATOR = new Creator<Joueur>() {
        @Override
        public Joueur createFromParcel(Parcel in) {
            return new Joueur(in);
        }

        @Override
        public Joueur[] newArray(int size) {
            return new Joueur[size];
        }
    };

    public int getIdJoueur(){ return idJoueur;}
    public String getPseudo(){ return pseudo;}
    public int getNbJetons(){ return nbJetons;}
    public void setIdJoueur(int idJoueur1){this.idJoueur=idJoueur1;}
    public void setPseudo(String pseudo1){this.pseudo=pseudo1;}
    public void setNbJetons(int nbJetons1){this.nbJetons=nbJetons1;}

    public String toString(){return "Ce joueur a pour id : "+idJoueur+ " et pour pseudo : "+pseudo;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idJoueur);
        dest.writeString(pseudo);
        dest.writeInt(nbJetons);
    }
}
