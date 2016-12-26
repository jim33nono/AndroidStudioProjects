package test01.evan.idv.app_31_animal_gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by study on 2016/1/8.
 */
public class AnimalAdapter extends BaseAdapter {
    private Animal[] animals;
    private LayoutInflater inflater;
    private Context context;

    public AnimalAdapter(Animal[] animals, Context context) {
        this.animals = animals;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return animals.length;
    }

    @Override
    public Object getItem(int position) {
        return animals[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.animal, null);
        ImageView iv_animal = (ImageView) convertView.findViewById(R.id.iv_animal);
        Picasso.with(context).load(animals[position].album_file).into(iv_animal);
        return convertView;
    }
}
