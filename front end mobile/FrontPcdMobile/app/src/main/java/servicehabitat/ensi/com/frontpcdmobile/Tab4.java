package servicehabitat.ensi.com.frontpcdmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;

import servicehabitat.ensi.com.frontpcdmobile.entity.Configuration;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

/**
 * Created by Belal on 2/3/2016.
 */

public class Tab4 extends Fragment implements View.OnClickListener {


    Button submit;
    EditText profession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



      View v = inflater.inflate(R.layout.tab4, container, false);
      submit = (Button) v.findViewById(R.id.submitDemandeur);
      profession = (EditText) v.findViewById(R.id.profession);

      submit.setOnClickListener(this);

      return v;

    }

  @Override
  public void onClick(View view) {

        new PostAsync().execute(profession.getText().toString());

  }

  class PostAsync extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();
    private String LOGIN_URL = Configuration.adr+"/user/createDemandeur?cin="+ Session.personne.getCin()+"&profession=";



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
        Session.demandeur = true;
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

      }else
      {

        //ecech

      }


    }

  }


}
