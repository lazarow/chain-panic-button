package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import pl.nowakowski_arkadiusz.chain_panic_button.R;

public class AlarmEmailFormActivity extends AlarmLinkFormActivity {

    static final int PICK_CONTACT_REQUEST = 1;

    protected EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_email);
        setContentView(R.layout.activity_alarm_email_form);
        email = (EditText) findViewById(R.id.alarm_email_form_email);
    }

    public void pickEmail(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Email.ADDRESS};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
                email.setText(cursor.getString(column));
            }
        }
    }
}
