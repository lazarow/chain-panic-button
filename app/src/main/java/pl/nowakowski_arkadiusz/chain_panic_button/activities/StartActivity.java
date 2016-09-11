package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.dao.ChainLinkDAO;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;

/**
 * StartActivity is the main activity, it shows the timer, which starts the sending.
 */
public class StartActivity extends AppCompatActivity {

    private List<ChainLink> chainLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onResume() {
        ChainLinkDAO dao = new ChainLinkDAO(this);
        chainLinks = dao.findAll();
        dao.close();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manage_chain:
                // Start the chain list activity
                startActivity(new Intent(this, AlarmChainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
