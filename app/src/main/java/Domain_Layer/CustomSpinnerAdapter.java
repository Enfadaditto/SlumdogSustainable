package Domain_Layer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.slumdogsustainable.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<Integer> {
    private List<Integer> imageIds;
    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, List<Integer> imageIds) {
        super(context, R.layout.spinner_item, imageIds);
        this.imageIds = imageIds;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spinner_item, parent, false);

        ImageView imageView = view.findViewById(R.id.spinner_item_image);
        imageView.setImageResource(imageIds.get(position));

        return view;
    }
}