package servicehabitat.ensi.com.frontpcdmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

public class RatingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {



  ArrayList<Demande> demandes = new ArrayList<>();
  ListView listView;
  BaseAdapter adap2;


  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.raiting, null);
    builder.setView(dialogView);
    builder.setTitle("Noter un service");

    final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);
    final EditText desc = (EditText) dialogView.findViewById(R.id.commentaireNote);
    // Add the buttons
    final int pos = i;

    builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {


      Demande d = demandes.get(pos);
      String description = desc.getText().toString();
      double nb = ratingBar.getRating();


        if(desc.length()==0)
        {
          Toast.makeText(getApplicationContext(),"Il faut saisir une description",Toast.LENGTH_SHORT).show();
          return;
        }

        new PostAsync().execute(d.getId()+"",d.getCin_frs(),d.getCin_demandeur(),Session.personne.getCin(),String.valueOf(nb),description);


      }
    });
    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {


      }
    });
    // Set other dialog properties


    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();


  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rating);


    listView = (ListView) findViewById(R.id.listeDemandeAcce);

    DemandeAccepteeAdapter adapterd = new DemandeAccepteeAdapter(this,
      R.layout.item_list_view_demandeacceptee, demandes);


    listView.setAdapter(adapterd);


    adap2 = (BaseAdapter) listView.getAdapter();

    new GetAsync(adap2).execute();
    adap2.notifyDataSetChanged();
    listView.setOnItemClickListener(this);

  }

  class GetAsync extends AsyncTask<String, String, JSONObject> {

    BaseAdapter baseAdapter;
    JSONParser jsonParser = new JSONParser();
    String LOGIN_URL = Configuration.adr+"/demande/demandesAccepter?cin="+Session.personne.getCin()+"&isFRS="+Session.frs+"&isDemandeur="+Session.demandeur;

    public GetAsync(BaseAdapter baseAdapter) {
      this.baseAdapter = baseAdapter;
    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();
        ArrayList<JSONObject> json = jsonParser.makeHttpRequestManyObject(
          LOGIN_URL, "GET", params);

        for (JSONObject j : json) {

          Demande d= new Demande();
          JSONObject contact = j.getJSONObject("contact");
          String positionGPS = j.getString("positionGps");

          JSONObject identif = contact.getJSONObject("id");
          d.setId(identif.getInt("contact_id"));
          d.setCin_demandeur(identif.getString("demandeur_id"));
          d.setCin_frs(identif.getString("fournisseur_id"));
          d.setTitre(contact.getString("titre"));
          d.setDescription(contact.getString("description"));
          d.setLongitude(Double.parseDouble(positionGPS.split("\\*")[0]));
          d.setLatitude(Double.parseDouble(positionGPS.split("\\*")[1]));

          demandes.add(d);
        }
        this.baseAdapter.notifyDataSetChanged();
        for(Demande d : demandes)
        {
          System.out.println(d.getTitre());
        }
        if (json != null) {


          return null;

        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

    protected void onPostExecute(JSONObject json) {

      this.baseAdapter.notifyDataSetChanged();

    }
  }

  class PostAsync extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/note/ajouterNote";



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();


        LOGIN_URL+="?id="+args[0]+"&fournisseur="+args[1]+"&demandeur="+args[2]+"&auteur="+args[3]+"&score="+args[4]+"&commentaire="+args[5];

        JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

        if (json != null) {
          return json;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

    protected void onPostExecute(JSONObject json) {


      if (json != null) {

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

      }else
      {

        //ecech

      }


    }

  }

}
