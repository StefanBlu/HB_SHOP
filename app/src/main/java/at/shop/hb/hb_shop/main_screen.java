package at.shop.hb.hb_shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class main_screen extends AppCompatActivity
        implements menu_screen.NavigationDrawerCallbacks {

    private menu_screen mHbFragment;

    private static final String TAG = "Main";

    private LinearLayout ll_searchbar;

    private boolean serach_open = false;
    private boolean menu_open = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent myIntent = new Intent(this, loading_screen.class);
        startActivity(myIntent);

        setContentView(R.layout.activity_hb_shop_main);

        set_up_customactionbar();

        ll_searchbar = (LinearLayout) findViewById(R.id.ll_searchbar);


        mHbFragment = (menu_screen) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mHbFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    /**
     * set up actionbar with customlaylout
     */
    private void set_up_customactionbar() {
        assert getSupportActionBar() != null;
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setElevation(20);

        View customView = getLayoutInflater().inflate(R.layout.actionbar, null);
        actionBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0,0);

        actionBar.getCustomView().findViewById(R.id.logo_fragment).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v(TAG, "click open_drawer");
                        ll_searchbar.setVisibility(View.INVISIBLE);
                        if (!menu_open) {
                            mHbFragment.openDrawer();
                        } else {
                            mHbFragment.closeDrawer();
                        }
                        menu_open = !menu_open;
                    }
                });

        actionBar.getCustomView().findViewById(R.id.logo_lupe).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v(TAG, "click open_searchbar");
                        open_searchbar();
                        mHbFragment.closeDrawer();
                        ImageView img = (ImageView) findViewById(R.id.logo_lupe);
                        if (!serach_open) {
                            img.setImageResource(R.drawable.search_down);
                        } else {
                            img.setImageResource(R.drawable.lupe_icon);
                        }
                        serach_open = !serach_open;
                    }
                });

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }
    }

    /*
    ---------- Searchbar -------------

     */
    public void open_searchbar() {
        if (ll_searchbar.getVisibility() == View.VISIBLE) {
            ll_searchbar.setVisibility(View.INVISIBLE);
            getSupportActionBar().setElevation(20);
        } else {
            ll_searchbar.setVisibility(View.VISIBLE);
            getSupportActionBar().setElevation(0);
        }
    }

    public void cl_product(View view) {
        Log.v(TAG, "click_product_x");
        Intent myIntent = new Intent(this, product_detail.class);
        startActivity(myIntent);

    }


    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_hb_shop_main_test, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((main_screen) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
