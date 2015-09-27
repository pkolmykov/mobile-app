package guru.drinkit.listview;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.text.Html.fromHtml;

/**
 * Created by pkolmykov on 9/27/2015.
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {
    public RecipeAdapter(Context context, List<Recipe> objects) {
        super(context, R.layout.recipe_view, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.recipe_view, parent, false);
        Recipe recipe = getItem(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail);
        Picasso.with(getContext()).load("http://prod-drunkedguru.rhcloud.com" + recipe.getThumbnailUrl()).into(imageView);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        nameView.setText(recipe.getName());
        TextView descView = (TextView) view.findViewById(R.id.description);
        descView.setText(fromHtml(recipe.getDescription()));
        return view;
    }
}
