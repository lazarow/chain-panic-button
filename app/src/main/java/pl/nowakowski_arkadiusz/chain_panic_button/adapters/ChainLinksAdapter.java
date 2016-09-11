package pl.nowakowski_arkadiusz.chain_panic_button.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLinkType;

public class ChainLinksAdapter extends ArrayAdapter<ChainLink> {
    private final Context context;
    private final List<ChainLink> objects;

    public ChainLinksAdapter(Context context, int resource, List<ChainLink> objects) {
        super(context, -1, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.chain_link_row, parent, false);
        TextView nameView = (TextView) row.findViewById(R.id.chain_link_row_name);
        TextView subNameView = (TextView) row.findViewById(R.id.chain_link_row_subname);
        nameView.setText(objects.get(position).getName());
        if (objects.get(position).getType().equals(ChainLinkType.SMS)) {
            subNameView.setText(objects.get(position).getPhone());
        } else if (objects.get(position).getType().equals(ChainLinkType.EMAIL)) {
            subNameView.setText(objects.get(position).getEmail());
        } else if (objects.get(position).getType().equals(ChainLinkType.CALL)) {
            subNameView.setText(objects.get(position).getPhone());
        }
        return row;
    }
}
