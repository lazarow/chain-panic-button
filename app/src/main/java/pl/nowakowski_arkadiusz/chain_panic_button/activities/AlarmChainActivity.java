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
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.CallChainLinkFormActivity;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.EmailChainLinkFormActivityChain;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.SMSChainLinkFormActivityChain;
import pl.nowakowski_arkadiusz.chain_panic_button.adapters.ChainLinksAdapter;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;

public class AlarmChainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_chain);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_alarm_chain);
    }

    @Override
    protected void onResume() {
        List<ChainLink> chainLinks = new ArrayList<>();
        chainLinks.add(ChainLink.createCallChainLink(null, "Test 1", ""));
        chainLinks.add(ChainLink.createCallChainLink(null, "Test 2", ""));
        chainLinks.add(ChainLink.createCallChainLink(null, "Test 3", ""));
        ListView chain = (ListView) findViewById(R.id.chain);
        ChainLinksAdapter adapter = new ChainLinksAdapter(this, -1, chainLinks);
        chain.setAdapter(adapter);
        super.onResume();
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
                startActivity(new Intent(this, CallChainLinkFormActivity.class));
                break;
            case R.id.add_alarm_email:
                startActivity(new Intent(this, EmailChainLinkFormActivityChain.class));
                break;
            case R.id.add_alarm_sms:
                startActivity(new Intent(this, SMSChainLinkFormActivityChain.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
