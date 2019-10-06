package servicehabitat.ensi.com.frontpcdmobile;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

public class NoteActivity extends Fragment {


  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View v;
    v = inflater.inflate(R.layout.activity_note, container, false);

    return v;

  }


}
