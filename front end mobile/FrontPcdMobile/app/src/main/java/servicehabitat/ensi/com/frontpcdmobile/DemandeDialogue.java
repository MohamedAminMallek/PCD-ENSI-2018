package servicehabitat.ensi.com.frontpcdmobile;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;

/**
 * Created by Mohamed Amin on 22/04/2018.
 */

public class DemandeDialogue extends DialogFragment {

  private Demande demande;

  public Demande getDemande() {
    return demande;
  }

  public void setDemande(Demande demande) {
    this.demande = demande;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage("c'est titre test")
      .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          // FIRE ZE MISSILES!
        }
      })
      .setNegativeButton("Non", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          // User cancelled the dialog
        }
      });
    // Create the AlertDialog object and return it
    return builder.create();
  }
}
