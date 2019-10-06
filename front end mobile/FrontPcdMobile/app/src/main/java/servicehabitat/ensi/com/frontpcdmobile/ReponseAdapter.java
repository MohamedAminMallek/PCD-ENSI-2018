package servicehabitat.ensi.com.frontpcdmobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import servicehabitat.ensi.com.frontpcdmobile.entity.Demande;
import servicehabitat.ensi.com.frontpcdmobile.entity.Reponse;
import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

/**
 * Created by Mohamed Amin on 21/04/2018.
 */

public class ReponseAdapter extends ArrayAdapter<Reponse> {


  Context context;
  int layoutResourceId;
  ArrayList<Reponse> data = null;

  public ReponseAdapter(Context context, int layoutResourceId, ArrayList<Reponse> data) {
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
      holder.txtTitle = (TextView) row.findViewById(R.id.titrereponse);
      holder.sousTitre = (TextView) row.findViewById(R.id.descrireponse);


      row.setTag(holder);
    }
    else
    {
      holder = (WeatherHolder)row.getTag();
    }

    Reponse reponse = data.get(position);

    holder.txtTitle.setText(reponse.getTitre());
    holder.sousTitre.setText(reponse.getDesc());
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
