package servicehabitat.ensi.com.frontpcdmobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;

import servicehabitat.ensi.com.frontpcdmobile.entity.Session;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs

        Log.d("itemPag",position+"");
        switch (position) {
            case 0:
                if(Session.frs) {
                  Tab1 tab1 = new Tab1();
                  return tab1;
                }else
                {
                  Tab2 tab2 = new Tab2();
                  return tab2;
                }
            case 1:
                if(Session.demandeur)
                {
                  Log.d("Amin",Session.demandeur+"XX");
                  Tab3 tab3 = new Tab3();
                  return tab3;
                }else
                {
                  Log.d("Amin",Session.demandeur+"");
                  Tab4 tab4 = new Tab4();
                  return tab4;
                }
              /*case 2:
              {
                NoteActivity noteActivity = new NoteActivity();
                return noteActivity;

              }*/
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
