package pl.nowakowski_arkadiusz.chain_panic_button.activities.forms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.dao.ChainLinkDAO;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;

public abstract class ChainLinkFormActivity extends AppCompatActivity implements Validator.ValidationListener {

    protected ChainLink chainLink;
    protected Validator validator;
    private boolean isVerified = false;

    @NotEmpty(messageResId = R.string.error_empty)
    @Length(max = 20, messageResId = R.string.error_invalid)
    protected EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setting the back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Setting the validator
        validator = new Validator(this);
        validator.setValidationListener(this);
        // Setting fields
        name = (EditText) findViewById(R.id.input_name);
        // Load the model
        Intent intent = getIntent();
        chainLink = (ChainLink) intent.getSerializableExtra("chainLink");
        if (chainLink != null) {
            loadChainLinkData();
        }
        super.onCreate(savedInstanceState);
    }

    protected void loadChainLinkData() {
        name.setText(chainLink.getName());
    }

    protected void storeChainLinkData() {
        chainLink.setName(name.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Building the standard form menu
        getMenuInflater().inflate(R.menu.menu_chain_link, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Back to the previous activity
                finish();
                break;
            case R.id.save_the_link:
                validator.validate();
                if (isVerified) {
                    if (chainLink != null) {
                        ChainLinkDAO dao = new ChainLinkDAO(this);
                        storeChainLinkData();
                        if (chainLink.isNewRecord()) {
                            dao.insert(chainLink);
                        } else {
                            dao.update(chainLink);
                        }
                        dao.close();
                        Toast.makeText(this, R.string.chain_link_saved, Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationSucceeded() {
        isVerified = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
        isVerified = false;
    }
}
