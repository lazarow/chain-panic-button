package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import pl.nowakowski_arkadiusz.chain_panic_button.R;

public class AlarmSMSFormActivity extends AlarmLinkFormActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_sms);
        setContentView(R.layout.activity_alarm_smsform);
    }
}
