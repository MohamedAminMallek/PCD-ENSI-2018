package servicehabitat.ensi.com.frontpcdmobile.entity;

import com.google.android.gms.plus.model.people.Person;

/**
 * Created by Mohamed Amin on 22/04/2018.
 */

public class Demande {

  private int id;
  private Personne frs;
  private Personne demandeur;
  private String demandeurFullName;
  private int demandeurScore;
  private double demandeurEtoile;
  private String cin_frs;
  private String cin_demandeur;
  private String titre;
  private String description;
  private double longitude;
  private double latitude;


  public String getDemandeurFullName() {
    return demandeurFullName;
  }

  public void setDemandeurFullName(String demandeurFullName) {
    this.demandeurFullName = demandeurFullName;
  }

  public int getDemandeurScore() {
    return demandeurScore;
  }

  public void setDemandeurScore(int demandeurScore) {
    this.demandeurScore = demandeurScore;
  }

  public double getDemandeurEtoile() {
    return demandeurEtoile;
  }

  public void setDemandeurEtoile(double demandeurEtoile) {
    this.demandeurEtoile = demandeurEtoile;
  }

  public Personne getFrs() {
    return frs;
  }

  public void setFrs(Personne frs) {
    this.frs = frs;
  }

  public Personne getDemandeur() {
    return demandeur;
  }

  public void setDemandeur(Personne demandeur) {
    this.demandeur = demandeur;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Demande)) return false;

    Demande demande = (Demande) o;

    if (id != demande.id) return false;
    if (cin_frs != null ? !cin_frs.equals(demande.cin_frs) : demande.cin_frs != null) return false;
    return cin_demandeur != null ? cin_demandeur.equals(demande.cin_demandeur) : demande.cin_demandeur == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (cin_frs != null ? cin_frs.hashCode() : 0);
    result = 31 * result + (cin_demandeur != null ? cin_demandeur.hashCode() : 0);
    return result;
  }

  public Demande() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCin_frs() {
    return cin_frs;
  }

  public void setCin_frs(String cin_frs) {
    this.cin_frs = cin_frs;
  }

  public String getCin_demandeur() {
    return cin_demandeur;
  }

  public void setCin_demandeur(String cin_demandeur) {
    this.cin_demandeur = cin_demandeur;
  }

  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
