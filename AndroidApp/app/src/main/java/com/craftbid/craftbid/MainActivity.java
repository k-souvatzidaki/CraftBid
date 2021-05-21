package com.craftbid.craftbid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.craftbid.craftbid.adapters.FeedRecyclerAdapter;
import com.craftbid.craftbid.model.Thumbnail;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, AdapterView.OnItemSelectedListener {

    public static boolean logged_in = false;
    private List<Thumbnail> thumbnails;
    private RecyclerView recycler;
    private FeedRecyclerAdapter adapter;
    public static String username;
    public static boolean creator;
    public static final String GUEST = "@guest";
    public static final String MAIN = "@main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            username = bundle.getString("username", username);
            creator = bundle.getBoolean("creator", creator);
            if(!username.equals(GUEST)) logged_in = true;
        }
        Log.d("MAIN", "onCreate: username "+username);
        Log.d("MAIN", "onCreate: creator "+creator);

        //Change Toolbar Title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setTitle("feed");

//        AppBarLayout appBar = findViewById(R.id.appBar);
//        appBar.setExpanded(true);

        thumbnails = new ArrayList<>();
        thumbnails.add(new Thumbnail(0, "Πλεκτή τσάντα", "Ωραιότατη πλεκτή τσάντα πάρε πάρε όλα 5 ευρώ αρχική","Πλεκτά, Τσάντες", "bag", 5));
        thumbnails.add(new Thumbnail(1, "Βραχιόλια", "Βραχιολι χειροποιητο αν θες το παιρνεις", "Κοσμήματα",  "bracelet", 5));
        thumbnails.add(new Thumbnail(2, "Πλεκτά για όλους", "Πλεκτά ρούχα για όλες τις ηλικίε δεχόμαστε παραγγελίες", "Πλεκτά",  "knit", 15));
        thumbnails.add(new Thumbnail(3, "Πασχαλινό κερί χειροποίητο", "Κεριά Πασχαλινά για την Ανάσταση. Χρόνια Πολλά!", "Κεριά", "candle",  20));

        recycler = findViewById(R.id.feed_recyclerview);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(manager);
        adapter = new FeedRecyclerAdapter(thumbnails, this);
        recycler.setAdapter(adapter);

        Spinner sort = findViewById(R.id.sort_spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(spinner_adapter);
        sort.setOnItemSelectedListener(this);

        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.setOnEditorActionListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbarmenu, menu);
        if(logged_in){
            menu.findItem(R.id.login).setVisible(false);
        }else{
            menu.findItem(R.id.notif).setVisible(false);
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.profile).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.login:
            case R.id.logout:
                logged_in = false;
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                break;
            case R.id.profile:
                openPrivateProfile();
                break;
            case R.id.notif:
                openNotifications();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        invalidateOptionsMenu();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO sort items
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            filterSearch(view.getText().toString());
            view.clearFocus();
            recycler.requestFocus();
            hideKeyboard(this);
            handled = true;
        }
        return handled;
    }

    private void filterSearch(String text) {
//        ArrayList<Thumbnail> filteredList = new ArrayList<>();
        //TODO search in database and return results
        Log.d("SEARCH", "filterSearch: "+ text);

        adapter.filter(thumbnails);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openPrivateProfile() {
        Intent profile;
        if(creator){
            profile = new Intent(MainActivity.this, CreatorProfilePrivate.class);
        }else{
            profile = new Intent(MainActivity.this, CustomerProfilePrivate.class);
        }
        profile.putExtra("previous", MAIN);
        startActivity(profile);
    }

    private void openNotifications(){
        Intent notif = new Intent(MainActivity.this, NotificationsActivity.class);
        startActivity(notif);
    }

    //Temporary
    public int getDrawable(String name) {
        return this.getResources().getIdentifier(name, "drawable", this.getPackageName());
//        return ActivityCompat.getDrawable(this, resourceId);
    }

    public void reviewListing(int listing_id){
        // TODO set PRIVATE true/false based on whether user is the creator of the listing
        boolean PRIVATE = false;
        Intent listing_review;
        if(PRIVATE) listing_review = new Intent(MainActivity.this, ListingPrivateActivity.class);
        else listing_review = new Intent(MainActivity.this, ListingPublicActivity.class);
        listing_review.putExtra("listing_id", listing_id);
        listing_review.putExtra("previous", "@main");
        startActivity(listing_review);
    }
}