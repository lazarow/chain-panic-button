package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.CallChainLinkFormActivity;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.EmailChainLinkFormActivityChain;
import pl.nowakowski_arkadiusz.chain_panic_button.activities.forms.SMSChainLinkFormActivityChain;
import pl.nowakowski_arkadiusz.chain_panic_button.adapters.ChainLinksAdapter;
import pl.nowakowski_arkadiusz.chain_panic_button.dao.ChainLinkDAO;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLinkType;

public class AlarmChainActivity extends AppCompatActivity {

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.alarm_chain);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_alarm_chain);
        registerForContextMenu(findViewById(R.id.chain));
    }

    @Override
    protected void onResume() {
        ChainLinkDAO dao = new ChainLinkDAO(this);
        List<ChainLink> chainLinks = dao.findAll();
        dao.close();
        if (chainLinks.size() == 0) {
            Toast.makeText(AlarmChainActivity.this, R.string.no_chain_links, Toast.LENGTH_SHORT).show();
        }
        ListView chain = (ListView) findViewById(R.id.chain);
        ChainLinksAdapter adapter = new ChainLinksAdapter(this, -1, chainLinks);
        chain.setAdapter(adapter);
        updateOptionsMenu();
        super.onResume();
    }

    private void updateOptionsMenu() {
        ChainLinkDAO dao = new ChainLinkDAO(this);
        boolean callLinkExisting = dao.hasCallChainLink();
        dao.close();
        if (menu != null) {
            if (callLinkExisting) {
                menu.getItem(0).getSubMenu().getItem(1).setEnabled(false);
            } else {
                menu.getItem(0).getSubMenu().getItem(1).setEnabled(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm_chain, menu);
        this.menu = menu;
        updateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        ListView chain = (ListView) findViewById(R.id.chain);
        final ChainLink chainLink = (ChainLink) chain.getItemAtPosition(info.position);
        final MenuItem editItem = menu.add(R.string.edit);
        editItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = null;
                if (chainLink.getType().equals(ChainLinkType.SMS)) {
                    intent = new Intent(AlarmChainActivity.this, SMSChainLinkFormActivityChain.class);
                } else if (chainLink.getType().equals(ChainLinkType.EMAIL)) {
                    intent = new Intent(AlarmChainActivity.this, EmailChainLinkFormActivityChain.class);
                } else if (chainLink.getType().equals(ChainLinkType.CALL)) {
                    intent = new Intent(AlarmChainActivity.this, CallChainLinkFormActivity.class);
                }
                intent.putExtra("chainLink", chainLink);
                startActivity(intent);
                return false;
            }
        });
        final MenuItem removeItem = menu.add(R.string.remove);
        removeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ChainLinkDAO dao = new ChainLinkDAO(AlarmChainActivity.this);
                dao.delete(chainLink);
                dao.close();
                AlarmChainActivity.this.onResume();
                Toast.makeText(AlarmChainActivity.this, R.string.chain_link_removed, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_alarm_call:
                intent = new Intent(this, CallChainLinkFormActivity.class);
                intent.putExtra("chainLink", ChainLink.createCallChainLink(null, "", ""));
                break;
            case R.id.add_alarm_sms:
                intent = new Intent(this, SMSChainLinkFormActivityChain.class);
                intent.putExtra("chainLink", ChainLink.createSMSChainLink(null, "", "", false, false, ""));
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
