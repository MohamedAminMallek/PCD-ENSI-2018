package servicehabitat.ensi.com.frontpcdmobile.entity;

import java.io.File;
import java.util.Date;

/**
 * Created by Mohamed Amin on 08/04/2018.
 */

public class Personne {

  String cin;
  String nom;
  String prenom;
  int tel;
  String adresse;
  File photo;
  Date dateNaissance;
  String positionGps;
  int score = 0;
  double etoile;
  String username;
  String password;
  Date bannedUntil;

  public Personne() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Personne)) return false;

    Personne personne = (Personne) o;

    return cin.equals(personne.cin);

  }

  public double getEtoile() {
    return etoile;
  }

  public void setEtoile(double etoile) {
    this.etoile = etoile;
  }

  @Override
  public int hashCode() {
    return cin.hashCode();
  }

  public void setCin(String cin) {
    this.cin = cin;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public void setTel(int tel) {
    this.tel = tel;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public void setPhoto(File photo) {
    this.photo = photo;
  }

  public void setDateNaissance(Date dateNaissance) {
    this.dateNaissance = dateNaissance;
  }

  public void setPositionGps(String positionGps) {
    this.positionGps = positionGps;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setBannedUntil(Date bannedUntil) {
    this.bannedUntil = bannedUntil;
  }

  public String getCin() {
    return cin;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public int getTel() {
    return tel;
  }

  public String getAdresse() {
    return adresse;
  }

  public File getPhoto() {
    return photo;
  }

  public Date getDateNaissance() {
    return dateNaissance;
  }

  public String getPositionGps() {
    return positionGps;
  }

  public int getScore() {
    return score;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Date getBannedUntil() {
    return bannedUntil;
  }
}
