package com.example.murat.benimbebegim;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.murat.benimbebegim.adapters.TabsFragmentPagerAdapter;

public class ActivityHomeScreen extends FragmentActivity implements
        ActionBar.TabListener {
    // Sol Slider için Yapılmış özel layout android.support.v4 ün içinde
    private DrawerLayout mDrawerLayout;

    // Sol Slider Açıldığında Görünecek ListView
    private ListView mDrawerList;

    // Navigation Drawer nesnesini ActionBar'da gösterir.
    private ActionBarDrawerToggle mDrawerToggle;

    // ActionBar'ın titlesi dinamik olarak değişecek draweri açıp kapattıkça
    private String mTitle = "";

    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabsFragmentPagerAdapter tabsAdapter;
    private String[] days = new String[]{"Features", "Favorites", "More Events"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        // Content alanına fragment yüklemek için
        FragmentManager fragmentManager = getFragmentManager();


        FragmentTransaction ft = fragmentManager.beginTransaction();

        FragmentHome fragmentHome = new FragmentHome();
        ft.add(R.id.content_frame, fragmentHome);
        ft.commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        // iconu ve açılıp kapandığında görünecek texti veriyoruz.
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open,
                R.string.drawer_close) {

            // drawer kapatıldığında tetiklenen method
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();

            }

            // drawer açıldığında tetiklenen method
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Benim Bebeğim");
                invalidateOptionsMenu();
            }

        };

        // Açılıp kapanmayı dinlemek için register
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Navigationdaki Drawer için listview adapteri
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                R.layout.drawer_list_item, getResources().getStringArray(R.array.menu));

        // adapteri listviewe set ediyoruz
        mDrawerList.setAdapter(adapter);

        // actionbar home butonunu aktif ediyoruz
        getActionBar().setHomeButtonEnabled(true);

        // navigationu tıklanabilir hale getiriyoruz
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // sol slider açıldığında gelen listviewin tıklama eventi
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // itemleri arraya tekrar aldık
                String[] menuItems = getResources().getStringArray(R.array.menu);

                // dinamik title yapmak için actionbarda tıklananın titlesi görünecek
                mTitle = menuItems[position];

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // fragmenti contente yerleştirme.
                if (position == 0) {
                    FragmentHome fragmentHome = new FragmentHome();
                    ft.replace(R.id.content_frame, fragmentHome);
                    ft.commit();
                } else if (position == 1) {
                    FragmentAndroid fragmentAndroid = new FragmentAndroid();
                    ft.replace(R.id.content_frame, fragmentAndroid);
                    ft.commit();
                } else if (position == 2) {
                    FragmentIOS fragmentIOS = new FragmentIOS();
                    ft.replace(R.id.content_frame, fragmentIOS);
                    ft.commit();
                } else if (position == 3) {
                    FragmentWindowsPhone fragmentWindowsPhone = new FragmentWindowsPhone();
                    ft.replace(R.id.content_frame, fragmentWindowsPhone);
                    ft.commit();
                }

                // draweri kapat
                mDrawerLayout.closeDrawer(mDrawerList);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_opening, menu);
        return true;
    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //draweri sadece swipe ederek açma yerine sol tepedeki butona basarak açmak için
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        // navigationDrawer açıldığında ayarları gizlemek için
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }




}
