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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;
import servicehabitat.ensi.com.frontpcdmobile.entity.Metier;
import servicehabitat.ensi.com.frontpcdmobile.entity.Personne;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;


/**
 * Created by Mallek MOhamed AMin.
 */

public class Tab3 extends Fragment implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{


    ArrayList<String> tabOfStrong =new ArrayList<>();
    ArrayList<String> tabOfStrong2 =new ArrayList<>();
    ArrayList<String> tabOfStrong3 =new ArrayList<>();
    Spinner spinner;
    ListView listView;
    EditText titre;
    EditText desc;
    ArrayList<Metier> tab = new ArrayList<>();
    ArrayList<Personne> tab2 = new ArrayList<>();
    BaseAdapter adap2;
    int nbClick = 0;
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
      nbClick++;
        if(Session.nbDemande==0 && nbClick>1) {
          String met = spinner.getSelectedItem().toString();
          Metier mm = new Metier();
          mm.setTitre(met);
          long id = tab.get(tab.indexOf(mm)).getId();
          //new GetAsync2(adap2).execute(id + "");
          adap2.notifyDataSetChanged();
          Intent intent = new Intent(getContext(),MapsActivity.class);
          intent.putExtra("id_metier",id+"");
          startActivity(intent);
        }
    }

  public void toMain()
  {
    Intent intent = new Intent(this.getContext(), HomeActivity.class);
    startActivity(intent);
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {



    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    builder.setMessage("Annuler le demande ?")
      .setTitle(Session.demandes.get(i).getTitre());

    final int pos = i;

    // Add the buttons
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {



        Demande d= Session.demandes.get(pos);

        new PostAsync2().execute(d.getCin_frs(),d.getId()+"","annuler");

        Session.demandes.remove(pos);
        Session.nbDemande--;

        //mise a jour base

        toMain();

      }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        // User cancelled the dialog
      }
    });
    // Set other dialog properties


    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();



    return false;
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {





    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    builder.setMessage("Envoyer une demande à "+tab2.get(i).getNom()+" "+tab2.get(i).getPrenom()+" ?")
      .setTitle("Envoyer une demande");

    final int pos = i;

    // Add the buttons
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {





        Personne p =(Personne) tab2.get(pos);
        new PostAsync().execute(p.getCin()+"",titre.getText().toString(),desc.getText().toString(),p.getPositionGps());


      }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        // User cancelled the dialog
      }
    });
    // Set other dialog properties


    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();



  }

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    View v;

    if(Session.nbDemande>0){
      v = inflater.inflate(R.layout.liste_demande_en_attente, container, false);
      listView = (ListView) v.findViewById(R.id.demandesAttente);

      for(Demande d : Session.demandes)
        tabOfStrong3.add(d.getTitre()+" "+d.getDescription());

      DemandeAdapter adapterd = new DemandeAdapter(this.getActivity(),
        R.layout.item_list_view_demande, Session.demandes);
      listView.setAdapter(adapterd);
      adap2 = (BaseAdapter) listView.getAdapter();
      listView.setOnItemLongClickListener(this);

    }else{

      v = inflater.inflate(R.layout.tab3, container, false);
      //titre = (EditText) v.findViewById(R.id.titreDemande);
      //desc = (EditText) v.findViewById(R.id.descDemande);
      spinner = (Spinner) v.findViewById(R.id.TypeService);
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, tabOfStrong);
      spinner.setAdapter(adapter);
      spinner.setOnItemSelectedListener(this);
      BaseAdapter adap = (BaseAdapter) spinner.getAdapter();
      listView = (ListView) v.findViewById(R.id.listFrs);
      FournisseurAdapter adapterFRS = new FournisseurAdapter(this.getActivity(),
        R.layout.listview_item_row, tab2);
      listView.setAdapter(adapterFRS);
      adap2 = (BaseAdapter) listView.getAdapter();
      listView.setOnItemClickListener(this);
      new GetAsync(adap).execute();
    }
    return v;
    }


  class GetAsync extends AsyncTask<String, String, JSONObject> {


    JSONParser jsonParser = new JSONParser();
    private final String LOGIN_URL = Configuration.adr+"/metier/consulter";


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

        HashMap<String, String> params = new HashMap<>();
        ArrayList<JSONObject> json = jsonParser.makeHttpRequestManyObject(
          LOGIN_URL, "GET", params);
        int i = 0;
        for (JSONObject j : json) {
          Metier m = new Metier();
          m.setId(j.getLong("id"));
          m.setTitre(j.getString("titre"));
          m.setDescription(j.getString("description"));
          tab.add(m);
          tabOfStrong.add(m.getTitre());

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

      baseAdapter.notifyDataSetChanged();
      if (json != null) {


      }


    }


    }

  class GetAsync2 extends AsyncTask<String, String, JSONObject> {


    JSONParser jsonParser = new JSONParser();
    String LOGIN_URL = Configuration.adr+"/user/listFournisseur";


    BaseAdapter baseAdapter;

    public GetAsync2(BaseAdapter baseAdapter) {
      this.baseAdapter = baseAdapter;
    }

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
          tabOfStrong2.clear();
          tab2.clear();

        for (JSONObject j : json) {
          Personne p = new Personne();

          p.setCin(j.getString("cin"));
          p.setNom(j.getString("nom"));
          p.setPrenom(j.getString("prenom"));
          p.setScore(j.getInt("score"));
          p.setPositionGps(j.getString("positionGps"));
          if(!tab2.contains(p)) {
            if(!p.equals(Session.personne)) {
              tab2.add(p);
              tabOfStrong2.add(p.getNom() + " " + p.getPrenom() + " Score: " + p.getScore());
            }
          }
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
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

      }else
      {

        //ecech

      }


    }

  }

  class PostAsync2 extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/demande/repondreDemande?demandeur="+ Session.personne.getCin()+"&fournisseur=";



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
}



