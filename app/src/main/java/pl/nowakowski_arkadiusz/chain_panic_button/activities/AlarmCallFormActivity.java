package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import static android.provider.ContactsContract.CommonDataKinds.Phone;

import pl.nowakowski_arkadiusz.chain_panic_button.R;

public class AlarmCallFormActivity extends AlarmLinkFormActivity {

    static final int PICK_CONTACT_REQUEST = 1;

    protected EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_call);
        setContentView(R.layout.activity_alarm_call_form);
        phoneNumber = (EditText) findViewById(R.id.alarm_call_form_phone);
    }

    public void pickPhoneNumber(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(Phone.NUMBER);
                phoneNumber.setText(cursor.getString(column));
            }
        }
    }
}
