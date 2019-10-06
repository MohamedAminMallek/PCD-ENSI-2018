package servicehabitat.ensi.com.frontpcdmobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;
import servicehabitat.ensi.com.frontpcdmobile.entity.Personne;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button submit;
    private Button register;
    private String urlAdress;
    private int aux = 0;
    private String cin="";
    private String urlAdress2;
    private String urlAdress3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submit =(Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
      if(Session.login)
        {
          Intent intent = new Intent(getBaseContext(),AccueilActivity.class);
          startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {

      if(view.getId()==R.id.submit) {
        urlAdress = Configuration.adr+"/user/authentification?username=" + username.getText() + "&password=" + password.getText();

        if(username.length()==0)
        {
          username.setError("Ce champ ne doit pas être vide.");
          return;
        }
        if(password.length()==0)
        {
          password.setError("Ce champ ne doit pas être vide.");
          return;
        }

        String u = username.getText().toString();
        String p = password.getText().toString();
        new GetAsync().execute(u, p);
      }else
      {
        Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
        startActivity(intent);
      }


    }

  @Override
  protected void onRestart() {
    super.onRestart();
    if(Session.login) {
      Intent intent = new Intent(getBaseContext(), AccueilActivity.class);
      startActivity(intent);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if(Session.login)
    {
      Intent intent = new Intent(getBaseContext(),AccueilActivity.class);
      startActivity(intent);
    }
  }

  class GetAsync extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    private final String LOGIN_URL = Configuration.adr+"/user/authentification?username="+username.getText().toString()+"&password="+password.getText().toString();
    private static final String TAG_SUCCESS = "cin";
    private static final String TAG_MESSAGE = "nom";

    @Override
    protected void onPreExecute() {
      pDialog = new ProgressDialog(MainActivity.this);
      pDialog.setMessage("Attempting login...");
      pDialog.setIndeterminate(false);
      pDialog.setCancelable(true);
      pDialog.show();
    }


    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();
        //params.put("username", args[0]);
        //params.put("password", args[1]);
        JSONObject json = jsonParser.makeHttpRequest(
          urlAdress, "GET",params);
        if (json != null) {
          Log.d("JSON result", json.toString());
          return json;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

    protected void onPostExecute(JSONObject json) {

      int success = 0;
      String message = "";
      if (pDialog != null && pDialog.isShowing()) {
        pDialog.dismiss();
      }
      if (json != null) {

        String msg = "";
        try {
          cin = json.getString("cin");
          String nom = json.getString("nom");
          String prenom = json.getString("prenom");
          Personne p = new Personne();
          p.setPositionGps(json.getString("positionGps"));
          p.setCin(cin);
          //p.setBannedUntil(new Date(json.getString("bannedUntil")));

          Session.personne = p;
          Date currentTime = Calendar.getInstance().getTime();
          Date banned = new Date(json.getLong("bannedUntil"));
          Log.d("banned",banned.toString());
          if(banned.compareTo(currentTime)>0)
          {
            Toast.makeText(MainActivity.this, "Vous êtes sanctionné.",
              Toast.LENGTH_LONG).show();
              return;
          }
          msg += "Welcome "+nom+" "+prenom;
          Session.personne.setNom(nom);
          Session.personne.setPrenom(prenom);
        }catch (JSONException e)
        {
          e.printStackTrace();
        }

        Toast.makeText(MainActivity.this, msg,
          Toast.LENGTH_LONG).show();


        urlAdress2 = Configuration.adr+"/user/fournisseur?cin="+cin;
        new GetAsyncFrsAndDemandeur().execute("","");
      }else
      {

        Toast.makeText(MainActivity.this, "Vérifiez votre mot de passe ou votre nom d’utilisateur.",
          Toast.LENGTH_LONG).show();

      }
    }

  }
  class GetAsyncFrsAndDemandeur extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {
        String url = "";
        if(aux==0){
          url = urlAdress2;
        }else
          url = urlAdress3;
        HashMap<String, String> params = new HashMap<>();
        JSONObject json = jsonParser.makeHttpRequest(
          url, "GET",params);
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

        String msg = "";
        try {

          if(aux==0) {

            Session.frs = true;
            boolean des = json.getBoolean("disponibilite");
            Session.dispo = des;

          }else {

            Session.demandeur = true;
            String prof = json.getString("profession");

          }
        }catch (JSONException e)
        {
          e.printStackTrace();
        }



      }else
      {

      }
      Session.login = true;
      if(aux==0)
      {
        aux++;
        urlAdress3 = Configuration.adr+"/user/demandeur?cin="+cin;
        new GetAsyncFrsAndDemandeur().execute("","");
      }
      if(aux>0) {
        Log.d("hello D",Session.demandeur+"");
        if(Session.demandeur==true)
        {
          Log.d("Hello demande","dd");
          new GetAsync2().execute();

        }else {
          Intent intent = new Intent(getBaseContext(), AccueilActivity.class);
          startActivity(intent);
        }
      }
    }

  }

  class GetAsync2 extends AsyncTask<String, String, JSONObject> {


    JSONParser jsonParser = new JSONParser();
    String LOGIN_URL = Configuration.adr+"/demande/demandesbydemandeur?cin="+Session.personne.getCin();
    ArrayList<Demande> demandes = new ArrayList<>();
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
        Log.d("nb demande",demandes.size()+"");
        Session.nbDemande = demandes.size();
        Session.demandes = demandes;
        if (json != null) {
          return null;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

    protected void onPostExecute(JSONObject json) {

        Session.demandes = demandes;
        Session.nbDemande = demandes.size();

    }
  }

}
