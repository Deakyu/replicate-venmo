package com.example.deakyu.replicatevenmo.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.deakyu.replicatevenmo.NotificationActivity;
import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.ViewPagerAdapter;

public class FeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // TabLayout related variables
    private ViewPagerAdapter mViewPageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    // Toolbar, FAB, Navigation, etc...
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setupViewPagerWithTabLayout();
        setupToolbar();
        setupFAB();
        setupDrawer();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Set intent to go to SendMoenyActivity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // region TabLayout with ViewPager Methods
    private void setupViewPagerWithTabLayout() {
        mViewPager = findViewById(R.id.container);
        mTabLayout = findViewById(R.id.tabs);

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        mViewPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPageAdapter.addFragment(new MeFragment(), "ME");
        mViewPageAdapter.addFragment(new FriendsFragment(), "FRIENDS");
        mViewPageAdapter.addFragment(new PublicFragment(), "PUBLIC");
        viewPager.setAdapter(mViewPageAdapter);
    }
    // endregion

    // region Menu Methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notifications) {
            Intent intent = new Intent(FeedActivity.this, NotificationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    // endregion

    // region Navigation Methods
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_invite) {
            // Handle the camera action
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // endregion
}
