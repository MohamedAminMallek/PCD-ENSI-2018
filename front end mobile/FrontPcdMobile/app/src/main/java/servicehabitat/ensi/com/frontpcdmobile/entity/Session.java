package servicehabitat.ensi.com.frontpcdmobile.entity;

import java.util.ArrayList;

/**
 * Created by Mohamed Amin on 09/04/2018.
 */

public abstract class Session {

  public static boolean login = false;
  public static boolean frs = false;
  public static boolean dispo = false;

  public static boolean demandeur = false;

  public static int nbDemande = 0;
  public static ArrayList<Demande> demandes;


  public static Personne personne;



}
