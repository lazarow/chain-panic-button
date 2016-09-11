package pl.nowakowski_arkadiusz.chain_panic_button.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.models.links.Link;

public class LinksArrayAdapter extends ArrayAdapter<Link> {
    private final Context context;
    private final List<Link> objects;

    public LinksArrayAdapter(Context context, int resource, List<Link> objects) {
        super(context, -1, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_link, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.row_link_name);
        textView.setText(objects.get(position).getName());
        return row;
    }
}
