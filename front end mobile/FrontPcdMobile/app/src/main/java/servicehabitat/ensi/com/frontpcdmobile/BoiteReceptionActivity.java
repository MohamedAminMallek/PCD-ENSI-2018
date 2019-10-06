package servicehabitat.ensi.com.frontpcdmobile;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Metier;
import servicehabitat.ensi.com.frontpcdmobile.entity.Reponse;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

public class BoiteReceptionActivity extends AppCompatActivity {


  ListView listeReponse;
  BaseAdapter adap2;
  ArrayList<Reponse> reponses = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_boite_reception);

    listeReponse = (ListView) findViewById(R.id.listedesrep);

    ReponseAdapter reponseAdapter = new ReponseAdapter(this,R.layout.reponse_layout,reponses);
    listeReponse.setAdapter(reponseAdapter);
    adap2 = (BaseAdapter) listeReponse.getAdapter();
    new GetAsync(adap2).execute();

  }

  class GetAsync extends AsyncTask<String, String, JSONObject> {


    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/reponse/consulterReponse";


    BaseAdapter baseAdapter;

    public GetAsync(BaseAdapter baseAdapter) {
      this.baseAdapter = baseAdapter;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {
        LOGIN_URL += "?cin="+ Session.personne.getCin();
        HashMap<String, String> params = new HashMap<>();
        ArrayList<JSONObject> json = jsonParser.makeHttpRequestManyObject(
          LOGIN_URL, "GET", params);
        int i = 0;
        for (JSONObject j : json) {
          Reponse r = new Reponse();

          r.setTitre(j.getString("titre"));
          r.setDesc(j.getString("description"));
          reponses.add(r);
        }
        if (json != null) {
          return json.get(0);
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

    protected void onPostExecute(JSONObject json) {

      int success = 0;
      String message = "";
      if (json != null) {

        this.baseAdapter.notifyDataSetChanged();

      }


    }


  }


}
