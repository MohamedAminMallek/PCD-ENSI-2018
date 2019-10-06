package servicehabitat.ensi.com.frontpcdmobile;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import servicehabitat.ensi.com.frontpcdmobile.entity.Session;


public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home);

      //Initializing the tablayout
      tabLayout = (TabLayout) findViewById(R.id.tabLayout);

      //Adding the tabs using addTab() method
      if(Session.frs)
        tabLayout.addTab(tabLayout.newTab().setText("Voir mes demandes"));
      else
        tabLayout.addTab(tabLayout.newTab().setText("s'inscrire comme un Frs de service"));
      if(Session.demandeur) {
        if(Session.nbDemande>0) {
          Toast.makeText(this.getBaseContext(), "Vous Avez des Demande de contact en attente", Toast.LENGTH_LONG).show();
        }
        tabLayout.addTab(tabLayout.newTab().setText("Besoin d'un service"));
      }else
        tabLayout.addTab(tabLayout.newTab().setText("S'inscire comme un demandeur de service"));

  //    tabLayout.addTab(tabLayout.newTab().setText("Noter un service"));

      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

      //Initializing viewPager
      viewPager = (ViewPager) findViewById(R.id.pager);


//      Toast.makeText(this.getBaseContext(), tabLayout.getTabCount()+"", Toast.LENGTH_LONG).show();
      //Creating our pager adapter
      Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

      //Adding adapter to pager
      viewPager.setAdapter(adapter);

      //Adding onTabSelectedListener to swipe views
      tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
      viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
  }
