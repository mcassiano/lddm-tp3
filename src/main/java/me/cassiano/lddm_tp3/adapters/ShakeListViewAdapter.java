package me.cassiano.lddm_tp3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.cassiano.lddm_tp3.R;
import me.cassiano.lddm_tp3.models.Shake;

/**
 * Created by matheus on 5/7/15.
 */
public class ShakeListViewAdapter extends BaseAdapter {

    private List<Shake> shakes;
    private PrettyTime formatter;


    public ShakeListViewAdapter() {
        this.shakes = new ArrayList<>();
        this.formatter = new PrettyTime();
    }

    public ShakeListViewAdapter(List<Shake> shake) {
        this.shakes = new ArrayList<>();
        this.formatter = new PrettyTime();

        if (shake != null)
            this.shakes.addAll(shakes);
    }

    static class ShakeViewHolder {
        ImageView map;
        TextView lat;
        TextView lon;
        TextView date;
    }

    static class GoogleMapsStaticURL {

        public static String getURL(double latitude, double longitude) {

            return "http://maps.googleapis.com/maps/api/staticmap"
                    + "?center=" + Double.toString(latitude) + "," +
                    Double.toString(longitude) + "&zoom=16&size=640x480" +
                    "&markers=color:red%7C" + Double.toString(latitude) +
                    "," + Double.toString(longitude);
        }

    }

    public void addItem(Shake item) {
        shakes.add(item);
        sortDesc();
        notifyDataSetChanged();
    }

    public void addItems(List<Shake> item) {
        shakes.addAll(item);
        sortDesc();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shakes.size();
    }

    @Override
    public Object getItem(int position) {
        return shakes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ShakeViewHolder shakeViewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater =
                    (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
            shakeViewHolder = new ShakeViewHolder();

            shakeViewHolder.lat = (TextView) convertView.findViewById(R.id.lat);
            shakeViewHolder.lon = (TextView) convertView.findViewById(R.id.lon);
            shakeViewHolder.date = (TextView) convertView.findViewById(R.id.date);
            shakeViewHolder.map = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(shakeViewHolder);
        }

        else {
            shakeViewHolder = (ShakeViewHolder) convertView.getTag();
        }

        Shake shake = (Shake) getItem(position);

        String txtLatitude = parent.getContext().getString(R.string.txt_latitude);
        String txtLongitude = parent.getContext().getString(R.string.txt_longitude);
        String txtDate = parent.getContext().getString(R.string.txt_date);

        shakeViewHolder.lat.setText(String.format(txtLatitude, shake.getLatitude()));
        shakeViewHolder.lon.setText(String.format(txtLongitude, shake.getLongitude()));
        shakeViewHolder.date.setText(String.format(txtDate, formatter.format(shake.getDate())));

        String gStatic = GoogleMapsStaticURL.getURL(shake.getLatitude(), shake.getLongitude());
        Picasso.with(parent.getContext()).load(gStatic).into(shakeViewHolder.map);

        return convertView;

    }

    private void sortDesc() {

        Collections.sort(shakes, new Comparator<Shake>() {
            @Override
            public int compare(Shake lhs, Shake rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        });

    }
}
