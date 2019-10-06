package servicehabitat.ensi.com.frontpcdmobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Metier;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

/**
 * Created by Belal on 2/3/2016.
 */

public class Tab2 extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {


    ArrayList<Metier> tab = new ArrayList<Metier>();
    ArrayList<String> tabOfStrong = new ArrayList<String>();
    Spinner spinner;
    Button insc;
    String[] valuees = new String[100];
    Button proposer;
    EditText titreProp;
    EditText descProp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      View v = inflater.inflate(R.layout.tab2, container, false);

      spinner = (Spinner) v.findViewById(R.id.listMetier);
      spinner.setOnItemSelectedListener(this);

      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, tabOfStrong);
      //adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

      spinner.setAdapter(adapter);
      BaseAdapter adap = (BaseAdapter) spinner.getAdapter();
      adap.notifyDataSetChanged();
      insc = (Button) v.findViewById(R.id.inscrireFrs);
      insc.setOnClickListener(this);
      new GetAsync(adap).execute();

      proposer = (Button) v.findViewById(R.id.submitprop);
      titreProp = (EditText) v.findViewById(R.id.titreprop);
      descProp = (EditText) v.findViewById(R.id.descprop);
      proposer.setOnClickListener(this);

      return v;

    }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    //Toast.makeText(this.getActivity(), spinner.getSelectedItem().toString() + "",
      //Toast.LENGTH_LONG).show();
  }

  @Override
  public void onClick(View view) {


    if(view.getId()==R.id.inscrireFrs){

      Metier m = new Metier();
      m.setTitre(spinner.getSelectedItem().toString());
      m = tab.get(tab.indexOf(m));
      new PostAsync().execute(m.getId()+"");
    }else
    {
      String t = titreProp.getText().toString();
      String d = descProp.getText().toString();
      new PostAsync2().execute(Session.personne.getCin(),t,d);



    }


  }


  class PostAsync extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/user/createFournisseur?cin="+ Session.personne.getCin()+"&id_metier=";



    @Override
    protected void onPreExecute() {

    }

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

    protected void onPostExecute(JSONObject json) {


      if (json != null) {

        //succée
        Session.frs = true;
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

      }else
      {

        //ecech

      }


    }

  }

  class GetAsync extends AsyncTask<String, String, JSONObject> {



    JSONParser jsonParser = new JSONParser();
    private final String LOGIN_URL = Configuration.adr+"/metier/consulter";


    BaseAdapter baseAdapter;
    public GetAsync(BaseAdapter baseAdapter)
    {
      this.baseAdapter = baseAdapter;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {

      try {

        HashMap<String, String> params = new HashMap<>();
        ArrayList<JSONObject> json = jsonParser.makeHttpRequestManyObject(
          LOGIN_URL, "GET", params);
        int i = 0;
        valuees = new String[json.size()];
        for(JSONObject j : json)
        {
          Metier m = new Metier();
          m.setId(j.getLong("id"));
          m.setTitre(j.getString("titre"));
          m.setDescription(j.getString("description"));
          tab.add(m);
          tabOfStrong.add(m.getTitre());
          valuees[i] = m.getTitre();
        }
        baseAdapter.notifyDataSetChanged();
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



      }


    }

  }

  class PostAsync2 extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/proposition/ajouterProposition";



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

        LOGIN_URL+="?cin="+args[0]+"&titre="+args[1]+"&description="+args[2];

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


        Toast.makeText(getContext(),"Votre Proposition est envoyée avec succès",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getContext(), AccueilActivity.class);
        startActivity(intent);

      }else
      {

        //ecech

      }


    }

  }
}
