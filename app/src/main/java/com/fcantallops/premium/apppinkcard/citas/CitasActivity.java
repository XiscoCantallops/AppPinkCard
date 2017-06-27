package com.fcantallops.premium.apppinkcard.citas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fcantallops.premium.apppinkcard.R;


public class CitasActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Fragment mCitasFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   // Redirección al Login
        //if (!UserPrefs.getInstance(this).isLoggedIn()) {
        if(true){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }*/

        setContentView(R.layout.activity_citas);



        // Referencias UI
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCitasFragment = getSupportFragmentManager().findFragmentById(R.id.Citas_container);

        // Setup
        setUpToolbar();
        setUpCitasFragment();


    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void setUpCitasFragment() {
        if (mCitasFragment == null) {
            mCitasFragment = CitasFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.Citas_container, mCitasFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_citas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
