package servicehabitat.ensi.com.frontpcdmobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

/**
 * Created by Mohamed Amin on 21/04/2018.
 */

public class DemandeAccepteeAdapter extends ArrayAdapter<Demande> {


  Context context;
  int layoutResourceId;
  ArrayList<Demande> data = null;

  public DemandeAccepteeAdapter(Context context, int layoutResourceId, ArrayList<Demande> data) {
    super(context, layoutResourceId, data);
    this.layoutResourceId = layoutResourceId;
    this.context = context;
    this.data = data;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    WeatherHolder holder = null;

    if(row == null)
    {
      LayoutInflater inflater = ((Activity)context).getLayoutInflater();
      row = inflater.inflate(layoutResourceId, parent, false);

      holder = new WeatherHolder();
      //holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
      holder.txtTitle = (TextView) row.findViewById(R.id.txtTitleDemande);
      holder.sousTitre = (TextView) row.findViewById(R.id.sousTitre);


      row.setTag(holder);
    }
    else
    {
      holder = (WeatherHolder)row.getTag();
    }

    Demande weather = data.get(position);

    holder.txtTitle.setText("Service : "+weather.getTitre());
    String msg ="";
    if(Session.personne.getCin().compareTo(weather.getCin_frs())==0)
      msg="Vous etes le fournisseur de ce service";
    else
      msg = "Vous etes le demandeur de ce service";
    holder.sousTitre.setText(msg);
    //holder.imgIcon.setImageResource(weather.icon);

    return row;
  }

  static class WeatherHolder
  {
    //ImageView imgIcon;
    TextView txtTitle;
    TextView sousTitre;
  }


}
