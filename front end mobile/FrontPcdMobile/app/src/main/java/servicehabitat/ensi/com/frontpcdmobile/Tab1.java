package servicehabitat.ensi.com.frontpcdmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;
import servicehabitat.ensi.com.frontpcdmobile.entity.Personne;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

/**
 * Created by Mallek Mohamed Amin.
 */

//Our class extending fragment
public class Tab1 extends Fragment implements AdapterView.OnItemLongClickListener,CompoundButton.OnCheckedChangeListener,View.OnClickListener{


    ListView listView;
    CheckBox checkBox;
    BaseAdapter adap2;
    ArrayList<String> listeAvis = new ArrayList<>();
    Personne selectedPersonne = new Personne();

    ArrayList<Demande> demandes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      View v;
      v = inflater.inflate(R.layout.tab1, container, false);
      listView = (ListView) v.findViewById(R.id.listeDemandes);
      DemandeAdapter adapterd = new DemandeAdapter(this.getActivity(),
        R.layout.item_list_view_demande, demandes);
      listView.setAdapter(adapterd);
      adap2 = (BaseAdapter) listView.getAdapter();

      new GetAsync(adap2).execute();
      adap2.notifyDataSetChanged();
      listView.setOnItemLongClickListener(this);



      checkBox = (CheckBox) v.findViewById(R.id.dispo);
      checkBox.setOnCheckedChangeListener(this);
      if(Session.dispo==true) {
        checkBox.setChecked(true);
      }
      return v;

    }

  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    if(checkBox.isChecked() != Session.dispo)
    {
      boolean disponibilite = checkBox.isChecked();
      Session.dispo = disponibilite;
      new PostAsync().execute(disponibilite+"");
    }

  }

  @Override
  public void onClick(View view) {

    final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
    LayoutInflater inflater = this.getActivity().getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.liste_avis_users, null);
    builder.setView(dialogView);
    builder.setTitle("Liste des avis");
    ListView listView = (ListView) dialogView.findViewById(R.id.listeavisuser);
    ArrayAdapter adapter = new ArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1,listeAvis);
    listView.setAdapter(adapter);

    // Add the buttons
    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
      }
    });
    // Set other dialog properties


    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();

  }

  @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {



    //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
    LayoutInflater inflater = this.getActivity().getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.accepter_demande_dialog, null);
    builder.setView(dialogView);

    builder.setTitle("Répondre à une demande ");

    TextView client = (TextView) dialogView.findViewById(R.id.demandeurName);
    TextView avisClient = (TextView) dialogView.findViewById(R.id.avisdesusers);

    avisClient.setOnClickListener(this);

    TextView distance = (TextView) dialogView.findViewById(R.id.distance);


    Demande dd = demandes.get(i);
    selectedPersonne.setCin(dd.getCin_demandeur());

    new GetAsyncAvis().execute(selectedPersonne.getCin());

    client.setText("Client :"+dd.getDemandeurFullName());

    double long1 = Double.parseDouble(Session.personne.getPositionGps().split("\\*")[0]);
    double lat1 = Double.parseDouble(Session.personne.getPositionGps().split("\\*")[1]);
    double dis = distance(dd.getLatitude(),dd.getLongitude(), lat1,long1);
    NumberFormat formatter = new DecimalFormat("#0.00");
    distance.setText("Distance : "+formatter.format(dis)+" km");
    avisClient.setText("Avis des utilisateurs :"+dd.getDemandeurEtoile()+"/5"+ " ("+dd.getDemandeurScore()+")");



    //builder.setMessage("Accepter cette demande ? Si vous acceptez cette demande votre numero sera envoyé au demandeur.")
     // .setTitle("Répondre à une demande");

    final int pos = i;

    // Add the buttons
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {

        Demande d = demandes.get(pos);
        new PostAsync2().execute(d.getCin_demandeur(),d.getId()+"","Accepter");
        demandes.remove(pos);
        adap2.notifyDataSetChanged();
      }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {

        Demande d = demandes.get(pos);
        new PostAsync2().execute(d.getCin_demandeur(),d.getId()+"","Refuser");
        demandes.remove(pos);
        adap2.notifyDataSetChanged();
      }
    });
    // Set other dialog properties


    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();


      return false;
    }
    class GetAsync extends AsyncTask<String, String, JSONObject> {

      BaseAdapter baseAdapter;
      JSONParser jsonParser = new JSONParser();
      String LOGIN_URL = Configuration.adr+"/demande/demandesbyfrs?cin="+Session.personne.getCin();

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
            String fullName = j.getString("fullName");
            int score = j.getInt("score");
            double etoule = j.getDouble("etoile");

            d.setDemandeurFullName(fullName);
            d.setDemandeurScore(score);
            d.setDemandeurEtoile(etoule);

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
    private String LOGIN_URL = Configuration.adr+"/user/updateFrs?cin="+ Session.personne.getCin()+"&disponibilite=";


    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();

        LOGIN_URL+=args[0];

        JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

        if (json != null) {
          return json;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

  }
    class PostAsync2 extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/demande/repondreDemande?fournisseur="+ Session.personne.getCin()+"&demandeur=";



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();


        LOGIN_URL+=args[0]+"&id="+args[1]+"&etat="+args[2];

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


      }else
      {

        //ecech

      }


    }

  }
    class GetAsyncAvis extends AsyncTask<String, String, JSONObject> {


    JSONParser jsonParser = new JSONParser();
    String LOGIN_URL = Configuration.adr+"/note/getNotesBySujet";


    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();
        LOGIN_URL += "?cin=" + args[0];
        ArrayList<JSONObject> json = jsonParser.makeHttpRequestManyObject(
          LOGIN_URL, "GET", params);
        int i = 0;
        listeAvis = new ArrayList<>();
        for (JSONObject j : json) {

          listeAvis.add(j.getString("commentaire"));
        }
        if (json != null) {
          return null;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

  }

  private double distance(double lat1, double lon1, double lat2, double lon2) {
    double theta = lon1 - lon2;
    double dist = Math.sin(deg2rad(lat1))
      * Math.sin(deg2rad(lat2))
      + Math.cos(deg2rad(lat1))
      * Math.cos(deg2rad(lat2))
      * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
    return (dist);
  }

  private double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
  }

  private double rad2deg(double rad) {
    return (rad * 180.0 / Math.PI);
  }
}
