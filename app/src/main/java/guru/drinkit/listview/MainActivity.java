package guru.drinkit.listview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;
import static org.apache.commons.collections4.CollectionUtils.filter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new HttpRequestTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Recipe[]> {

        public static final String RECIPES_URL = "http://prod-drunkedguru.rhcloud.com/rest/recipes/?criteria=%7B%22cocktailTypes%22%3A%5B%5D%2C%22options%22%3A%5B%5D%2C%22ingredients%22%3A%5B%5D%7D";

        @Override
        protected Recipe[] doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = createRestTemplate();
                return restTemplate.getForObject(new URI(RECIPES_URL), Recipe[].class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @NonNull
        private RestTemplate createRestTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            converter.setObjectMapper(objectMapper);
            restTemplate.getMessageConverters().add(converter);
            return restTemplate;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            List<Recipe> recipeList = new ArrayList<>(Arrays.asList(recipes));
            filter(recipeList, new Predicate<Recipe>() {
                @Override
                public boolean evaluate(Recipe object) {
                    return object.isPublished();
                }
            });
            RecipeAdapter recipeAdapter = new RecipeAdapter(MainActivity.this, recipeList);
            ListView listView = (ListView) findViewById(R.id.theListView);
            listView.setAdapter(recipeAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
