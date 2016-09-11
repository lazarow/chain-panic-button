package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.AlarmCallFormActivity;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.AlarmEmailFormActivity;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.AlarmSMSFormActivity;
import pl.nowakowski_arkadiusz.chain_panic_button.adapters.LinksArrayAdapter;
import pl.nowakowski_arkadiusz.chain_panic_button.models.links.AlarmCall;
import pl.nowakowski_arkadiusz.chain_panic_button.models.links.Link;

public class AlarmChainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_chain);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_alarm_chain);

        List<Link> links = new ArrayList<>();
        links.add(new AlarmCall("Test 1"));
        links.add(new AlarmCall("Test 2"));
        links.add(new AlarmCall("Test 3"));
        ListView chain = (ListView) findViewById(R.id.chain);
        LinksArrayAdapter adapter = new LinksArrayAdapter(this, -1, links);
        chain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm_chain, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_alarm_call:
                startActivity(new Intent(this, AlarmCallFormActivity.class));
                break;
            case R.id.add_alarm_email:
                startActivity(new Intent(this, AlarmEmailFormActivity.class));
                break;
            case R.id.add_alarm_sms:
                startActivity(new Intent(this, AlarmSMSFormActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
