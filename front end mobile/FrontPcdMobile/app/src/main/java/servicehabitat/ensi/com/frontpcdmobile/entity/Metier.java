package servicehabitat.ensi.com.frontpcdmobile.entity;

/**
 * Created by Mohamed Amin on 20/04/2018.
 */

public class Metier {

  private long id;
  private String titre;
  private String description;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Metier)) return false;

    Metier metier = (Metier) o;

    return titre.equals(metier.titre);

  }

  @Override
  public int hashCode() {
    return titre.hashCode();
  }

  public long getId() {

    return id;
  }

  public void setId(long id) {
    this.id = id;
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
  public String toString()
  {
    return  titre;
  }
}
