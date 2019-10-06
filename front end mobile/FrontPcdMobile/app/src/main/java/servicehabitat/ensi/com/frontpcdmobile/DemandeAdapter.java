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
import servicehabitat.ensi.com.frontpcdmobile.entity.Personne;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

/**
 * Created by Mohamed Amin on 21/04/2018.
 */

public class DemandeAdapter extends ArrayAdapter<Demande> {


  Context context;
  int layoutResourceId;
  ArrayList<Demande> data = null;

  public DemandeAdapter(Context context, int layoutResourceId, ArrayList<Demande> data) {
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

      row.setTag(holder);
    }
    else
    {
      holder = (WeatherHolder)row.getTag();
    }

    Demande weather = data.get(position);

    double long1 = Double.parseDouble(Session.personne.getPositionGps().split("\\*")[0]);
    double lat1 = Double.parseDouble(Session.personne.getPositionGps().split("\\*")[1]);
    double dis = distance(weather.getLatitude(),weather.getLongitude(), lat1,long1);
    NumberFormat formatter = new DecimalFormat("#0.00");
    holder.txtTitle.setText("Demande de Service ="+weather.getTitre()+ " : "+" (Distance= "+formatter.format(dis)+"km)");
    //holder.imgIcon.setImageResource(weather.icon);

    return row;
  }

  static class WeatherHolder
  {
    //ImageView imgIcon;
    TextView txtTitle;
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
