package servicehabitat.ensi.com.frontpcdmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;
import servicehabitat.ensi.com.frontpcdmobile.entity.Personne;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener,View.OnClickListener {

  private GoogleMap mMap;
  Personne selectedFrs = new Personne();
  ArrayList<Personne> personnes = new ArrayList<>();
  ArrayList<String> listeAvis = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
      .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);




  }


  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setOnMarkerClickListener(this);
    Intent intent = getIntent();
    String id_metier = intent.getExtras().getString("id_metier");
    new GetAsync2().execute(id_metier);


    // Add a marker in Sydney and move the camera
    double l = Double.parseDouble(Session.personne.getPositionGps().split("\\*")[0]);
    double la = Double.parseDouble(Session.personne.getPositionGps().split("\\*")[1]);

    NumberFormat formatter = new DecimalFormat("#0.00");

    //l = Double.parseDouble(formatter.format(l));
    //la = Double.parseDouble(formatter.format(la));

    LatLng sydney = new LatLng(la, l);
    Marker m=mMap.addMarker(new MarkerOptions().position(sydney).title("Vous"));
    m.setTag(Session.personne);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,20));
  }


  @Override
  public void onClick(View view) {

    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.liste_avis_users, null);
    builder.setView(dialogView);
    builder.setTitle("Liste des avis");
    ListView listView = (ListView) dialogView.findViewById(R.id.listeavisuser);
    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listeAvis);
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
  public boolean onMarkerClick(Marker marker) {


    final Personne p = (Personne) marker.getTag();
    selectedFrs = p;
    if(p.getCin().compareTo(Session.personne.getCin())!=0)
    {


      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      LayoutInflater inflater = this.getLayoutInflater();
      final View dialogView = inflater.inflate(R.layout.dialog_envoye_demande, null);
      builder.setView(dialogView);
      builder.setTitle("Envoyer une demande");

      TextView frsName = (TextView) dialogView.findViewById(R.id.nomdufrs);
      frsName.setText("Fournisseur : "+p.getNom()+" "+p.getPrenom());
      TextView score = (TextView) dialogView.findViewById(R.id.scoredufrs);
      NumberFormat formatter = new DecimalFormat("#0.00");
      score.setText("Avis des utilisateurs : "+formatter.format(p.getEtoile())+"/5 ( "+p.getScore()+" )");
      score.setOnClickListener(this);
      new GetAsync().execute(selectedFrs.getCin());
      // Add the buttons
      builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {

          EditText editTextTitre = (EditText) dialogView.findViewById(R.id.titreDemandeMaps);
          EditText editTextDesc = (EditText) dialogView.findViewById(R.id.descDemandeMaps);
          String titre = ((EditText) dialogView.findViewById(R.id.titreDemandeMaps)).getText().toString();

          String description = ((EditText)dialogView.findViewById(R.id.descDemandeMaps)).getText().toString();
          if(editTextTitre.length()==0)
          {
            Toast.makeText(getApplicationContext(),"Le champ TITRE ne doit pas être vide.",Toast.LENGTH_SHORT).show();
            editTextTitre.setError("Ce champ ne doit pas être vide.");
            return;
          }
          if(editTextDesc.length()==0)
          {
            Toast.makeText(getApplicationContext(),"Le champ DESCRIPTION ne doit pas être vide.",Toast.LENGTH_SHORT).show();
            editTextDesc.setError("Ce champ ne doit pas être vide.");
            return;
          }
          new PostAsync().execute(p.getCin(),titre,description,p.getPositionGps());

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


    return false;
  }

  class GetAsync2 extends AsyncTask<String, String, JSONObject> {


    JSONParser jsonParser = new JSONParser();
    String LOGIN_URL = Configuration.adr+"/user/listFournisseur";

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();
        LOGIN_URL += "?id=" + args[0];
        ArrayList<JSONObject> json = jsonParser.makeHttpRequestManyObject(
          LOGIN_URL, "GET", params);
        int i = 0;


        for (JSONObject j : json) {
          Personne p = new Personne();

          p.setCin(j.getString("cin"));
          p.setNom(j.getString("nom"));
          p.setPrenom(j.getString("prenom"));
          p.setScore(j.getInt("score"));
          p.setPositionGps(j.getString("positionGps"));
          p.setEtoile(j.getDouble("etoile"));

          personnes.add(p);

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

      for(Personne p: personnes)
      {
        double long1 = Double.parseDouble(p.getPositionGps().split("\\*")[0]);
        double lat1 = Double.parseDouble(p.getPositionGps().split("\\*")[1]);
        LatLng sydney = new LatLng(lat1, long1);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(sydney);
        markerOptions.title(p.getNom()+" "+p.getPrenom());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_GREEN)));
        Marker mm = mMap.addMarker((markerOptions));
        mm.setTag(p);
        //mMap.addMarker(new MarkerOptions().position(sydney).title(p.getNom()+" "+p.getPrenom()));
      }

    }
  }
  class PostAsync extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/demande/createDemande?demandeur="+ Session.personne.getCin()+"&fournisseur=";



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();


        LOGIN_URL+=args[0]+"&titre="+args[1]+"&description="+args[2];

        JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);




        if (json != null) {
          Log.d("demandeErg",json.toString());
          Demande d = new Demande();

          JSONObject identif = json.getJSONObject("id");
          d.setId(identif.getInt("contact_id"));
          d.setCin_demandeur(identif.getString("demandeur_id"));
          d.setCin_frs(identif.getString("fournisseur_id"));
          d.setTitre(json.getString("titre"));
          d.setDescription(json.getString("description"));
          double long2 = Double.parseDouble(args[3].split("\\*")[0]);
          double lat2 = Double.parseDouble(args[3].split("\\*")[1]);
          d.setLatitude(lat2);
          d.setLongitude(long2);
          Session.demandes.add(d);
          Session.nbDemande = Session.demandes.size();
          return json;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

    protected void onPostExecute(JSONObject json) {


      if (json != null) {



        //succée
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

      }else
      {

        //ecech

      }


    }

  }
  class GetAsync extends AsyncTask<String, String, JSONObject> {


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
}
