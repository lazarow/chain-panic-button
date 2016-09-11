package pl.nowakowski_arkadiusz.chain_panic_button.activities.forms;

import android.widget.CheckBox;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import pl.nowakowski_arkadiusz.chain_panic_button.R;

public abstract class AlarmMessageLinkFormActivity extends AlarmLinkFormActivity {

    @NotEmpty
    protected EditText message;
    protected CheckBox addLocation;
    protected CheckBox addPhoto;

    @Override
    protected void onStart() {
        super.onStart();
        message = (EditText) findViewById(R.id.input_message);
        addLocation = (CheckBox) findViewById(R.id.checkbox_add_location);
        addPhoto = (CheckBox) findViewById(R.id.checkbox_add_photo);
    }
}
