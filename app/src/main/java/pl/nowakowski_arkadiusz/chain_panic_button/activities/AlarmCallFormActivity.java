package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;

import pl.nowakowski_arkadiusz.chain_panic_button.R;

public class AlarmCallFormActivity extends AlarmLinkFormActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_call);
        setContentView(R.layout.activity_alarm_call_form);
    }
}