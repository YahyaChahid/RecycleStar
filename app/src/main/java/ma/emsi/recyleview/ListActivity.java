package ma.emsi.recyleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ma.emsi.recyleview.adapter.StarAdapter;
import ma.emsi.recyleview.beans.Star;
import ma.emsi.recyleview.service.StarService;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView ,  recyclerView1;
    private StarAdapter starAdapter = null;
    StarService service;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Teachers are Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Teachers")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        stars = new ArrayList<>();
        service = StarService.getInstance();
        if(service.size()<=0)
        init();

        recyclerView1 = findViewById(R.id.recycle_view1);
        //insérer le code
        starAdapter = new StarAdapter(this, service.findAll());

        recyclerView1.setAdapter(starAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

    }
    public void init(){
        service.create(new Star("Mohamed LACHGAR ", "https://www.exelib.net/images/mohamed-lachgar.jpg", 5));
        service.create(new Star("Ali IDMANSOUR", "https://www.exelib.net/images/ali-idmansour.jpg", 3.5f));
        service.create(new Star("Amine CHAOULID", "https://www.exelib.net/images/amine-chaoulid.jpg", 3));
        service.create(new Star("Hassan AIT MOULAY",
                "https://www.exelib.net/images/hassan-ait-moulay.jpg", 5));
        service.create(new Star("Abdelghani SAOUD", "https://www.exelib.net/images/abdelghani-saoud.jpg", 1));


    }
}