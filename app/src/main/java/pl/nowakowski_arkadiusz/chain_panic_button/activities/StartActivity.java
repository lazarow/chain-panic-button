package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.dao.ChainLinkDAO;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;

/**
 * StartActivity is the main activity, it shows the timer, which starts the sending.
 */
public class StartActivity extends AppCompatActivity {

    private TextView timer;
    private long startTime = 0L;
    private long limit = 10000; // The time span before sending notifications
    private Handler handler = new Handler();
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private List<ChainLink> chainLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        timer = (TextView) findViewById(R.id.timer);
        startTime = SystemClock.uptimeMillis();
        ChainLinkDAO dao = new ChainLinkDAO(this);
        List<ChainLink> chainLinks = dao.findAll();
        dao.close();
        if (chainLinks.size() == 0) {
            Toast.makeText(this, R.string.no_chain_links, Toast.LENGTH_SHORT).show();
        } else {
            handler.postDelayed(updateTimerThread, 0);
        }
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

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = Math.max(0, limit - (timeSwapBuff + timeInMilliseconds));
            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
                    + ":" + String.format("%02d", milliseconds));
            handler.postDelayed(this, 0);
            if (updatedTime == 0) {
                handler.removeCallbacks(updateTimerThread);
            }
        }
    };
}
