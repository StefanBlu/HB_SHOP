package at.shop.hb.hb_shop;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * menu_screen used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class menu_screen extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private boolean cl_cat0 = false;
    private boolean cl_cat1 = false;
    private boolean cl_cat2 = false;
    private boolean cl_cat3 = false;

    public menu_screen() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //TODO:: Brauch ich das??
        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);


        //TODO: will ich wieder zum alten status oder jedes mal neu beim öffnen ???
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        //TODO: Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mDrawerListView = initList();

        return mDrawerListView;
    }

    private ListView initList()
    {

        ArrayList<category> list = new ArrayList<category>();
        list.add(new category(getResources().getStringArray(R.array.section_0)));
        list.add(new category(getResources().getStringArray(R.array.section_1)));
        list.add(new category(getResources().getStringArray(R.array.section_2)));
        list.add(new category(getResources().getStringArray(R.array.section_3)));

        UsersAdapter adapter = new UsersAdapter(getActivity(),list);

        mDrawerListView.setAdapter(adapter);

        return mDrawerListView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.

        //TODO: ??? was ist das??
        setHasOptionsMenu(true);
    }

    //------------------------------- end init ---------------------------


    public boolean isDrawerOpen()
    {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void openDrawer()
    {
        mDrawerLayout.openDrawer(Gravity.START);
    }
    public void closeDrawer()
    {
        mDrawerLayout.closeDrawer(Gravity.START);
    }


    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout)
    {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main_screen content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    public class UsersAdapter extends ArrayAdapter<category>
    {
        public UsersAdapter(Context context, ArrayList<category> cat)
        {
            super(context, 0, cat);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            category cat = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            LinearLayout list_ll = (LinearLayout) convertView.findViewById(R.id.list_ll_main);


            boolean temp = false;
            //Main category
            TextView main_cat = new TextView(getContext());
            main_cat.setText(cat.main);
            main_cat.setTypeface(null, Typeface.BOLD);

            main_cat.setId(position);

            main_cat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(v.getId() == 0)
                    {
                        cl_cat0 = !cl_cat0;
                    }
                    else if(v.getId() == 1)
                    {
                        cl_cat1 = !cl_cat1;
                    }
                    else if(v.getId() == 2)
                    {
                        cl_cat2 = !cl_cat2;
                    }
                    else if(v.getId() == 3)
                    {
                        cl_cat3 = !cl_cat3;
                    }

                    initList();
                    //TODO: Add Action
                }
            });

            list_ll.addView(main_cat);

            //sub category
            LinearLayout ll;
            ImageView img;
            TextView sub_cat;

            if (cl_cat0 && position == 0 || cl_cat1 && position == 1 || cl_cat2 && position == 2 || cl_cat3 && position == 3) {
                for (int i = 0; i < cat.sub.length; i++)
                {
                    ll = new LinearLayout(getContext());
                    ll.setGravity(LinearLayout.HORIZONTAL);
                    ll.setGravity(Gravity.CENTER_VERTICAL);
                    ll.setPadding(10, 2, 2, 2);

                    img = new ImageView(getContext());
                    img.setImageResource(R.drawable.list_item);

                    ll.addView(img);

                    sub_cat = new TextView(getContext());
                    sub_cat.setText(cat.sub[i]);
                    sub_cat.setTextSize((int) getResources().getDimension(R.dimen.drawer_txt_size));
                    sub_cat.isClickable();
                    sub_cat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            TextView txt = (TextView) v;
                            Log.v("todo", "Click " + txt.getText());

                            //TODO: Add Action
                        }
                    });

                    ll.addView(sub_cat);

                    list_ll.addView(ll);
                }
            }


            return convertView;
        }
    }
}
