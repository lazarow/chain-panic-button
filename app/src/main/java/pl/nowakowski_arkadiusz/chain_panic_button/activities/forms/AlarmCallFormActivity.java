package pl.nowakowski_arkadiusz.chain_panic_button.activities.forms;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import static android.provider.ContactsContract.CommonDataKinds.Phone;

import pl.nowakowski_arkadiusz.chain_panic_button.R;

public class AlarmCallFormActivity extends AlarmLinkFormActivity {

    static final int PICK_CONTACT_REQUEST = 1;

    @NotEmpty(messageResId = R.string.error_empty)
    @Length(max = 20, messageResId = R.string.error_invalid)
    protected EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_call);
        setContentView(R.layout.activity_form_alarm_call);
    }

    @Override
    protected void onStart() {
        super.onStart();
        phone = (EditText) findViewById(R.id.input_phone);
    }

    /**
     * Picks a phone number from contacts
     * @param view
     */
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
                phone.setText(cursor.getString(column));
            }
        }
    }
}
