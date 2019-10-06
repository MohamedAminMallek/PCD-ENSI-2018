package servicehabitat.ensi.com.frontpcdmobile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,LocationListener {


  EditText nom ;
  EditText prenom ;
  EditText adresse;
  EditText cin ;
  EditText username;
  EditText password;
  EditText tel;

  Button confirmer;

  public LocationManager locationManager;
  private double latitude;
  private double longitude;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    nom = (EditText) findViewById(R.id.nom);
    prenom = (EditText) findViewById(R.id.prenom);
    adresse = (EditText) findViewById(R.id.adresse);
    cin = (EditText) findViewById(R.id.cin);
    username =(EditText) findViewById(R.id.username);
    password = (EditText) findViewById(R.id.password);
    tel = (EditText) findViewById(R.id.tel);

    Button confirmer = (Button) findViewById(R.id.confirmer);
    confirmer.setOnClickListener(this);


    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(RegisterActivity.this, "plz no",Toast.LENGTH_LONG).show();
      return;
    }
    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    onLocationChanged(location);


  }

  @Override
  public void onLocationChanged(Location location) {

    if(location!=null) {
      //latitude et longitude info
      latitude = location.getLatitude();
      longitude = location.getLongitude();
    }
    Toast.makeText(RegisterActivity.this, longitude+" "+latitude,Toast.LENGTH_LONG).show();

  }


  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {
  }

  @Override
  public void onProviderEnabled(String s) {
  }

  @Override
  public void onProviderDisabled(String s) {
  }


  @Override
  public void onClick(View view) {


    if(nom.length()==0)
    {
      nom.setError("Ce champ ne doit pas être vide.");
      return;
    }
    if(prenom.length()==0)
    {
      prenom.setError("Ce champ ne doit pas être vide.");
      return;
    }
    if(cin.length()<8)
    {
      cin.setError("Le numéro de la CIN doit être composé de 8 chiffres.");
      return;
    }
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
    if(adresse.length()==0)
    {
      adresse.setError("Ce champ ne doit pas être vide.");
      return;
    }
    if(tel.length()<8)
    {
      tel.setError("Le numéro de téléphone doit être composé de 8 chiffres.");
      return;
    }


    new PostAsync().execute(cin.getText().toString(),
                            nom.getText().toString(),
                            prenom.getText().toString(),
                            adresse.getText().toString(),
                            username.getText().toString(),
                            password.getText().toString(),
                            tel.getText().toString()
                            );



  }
  class PostAsync extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private final String LOGIN_URL = Configuration.adr+"/user/createPersonne";

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();
        params.put("cin",args[0]);
        params.put("nom", args[1]);
        params.put("prenom", args[2]);
        params.put("adresse", args[3]);
        params.put("userName", args[4]);
        params.put("passWord", args[5]);
        params.put("tel", args[6]);
        params.put("score","0");
        params.put("positionGps",longitude+"*"+latitude);
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

        Toast.makeText(RegisterActivity.this, "Inscription avec succès.",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);


      }else
      {
        Toast.makeText(RegisterActivity.this, "Vérifiez vos données.",Toast.LENGTH_LONG).show();

      }


    }

  }

}
