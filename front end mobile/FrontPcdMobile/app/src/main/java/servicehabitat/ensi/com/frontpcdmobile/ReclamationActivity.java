package servicehabitat.ensi.com.frontpcdmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

public class ReclamationActivity extends AppCompatActivity implements View.OnClickListener {


  EditText titre;
  EditText desc;
  Button submit;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reclamation);

    titre = (EditText) findViewById(R.id.titrerecl);
    desc = (EditText) findViewById(R.id.descrecl);
    submit = (Button) findViewById(R.id.submitrecl);

    submit.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {

    if (view.getId() == submit.getId()) {
      if (TextUtils.isEmpty(titre.getText())) {
        titre.setError("Le titre ne doit pas etre vide");
        return;
      }
      if (TextUtils.isEmpty(desc.getText())) {
        desc.setError("Il faut saisir une description");
        return;
      }
      if (titre.getText().length()<5) {
        titre.setError("Il faut saisir au moins 5 caractères.");
        return;
      }
      if (desc.getText().length()<10) {
        desc.setError("Il faut saisir au moins 10 caractères.");
        return;
      }

      new PostAsync2().execute(Session.personne.getCin(),titre.getText().toString(),desc.getText().toString());



    }
  }


  class PostAsync2 extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/message/ajouterMessage";



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();

        JSONObject jj = new JSONObject();
        //jj.put("cin",args[0]);
        //params.put("emetteur",jj+"");
        //params.put("titre",args[1]);
        //params.put("description",args[2]);
        //params.put("reponse","false");

        LOGIN_URL+="?cin="+args[0]+"&titre="+args[1]+"&desc="+args[2];

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


        Toast.makeText(getApplicationContext(),"Votre reclamation est envoyée avec succès",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
        startActivity(intent);

      }else
      {

        //ecech

      }


    }

  }

}
