package org.willemsens.player.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.willemsens.player.R;
import org.willemsens.player.albums.AlbumsFragment;
import org.willemsens.player.artists.ArtistsFragment;
import org.willemsens.player.settings.SettingsFragment;
import org.willemsens.player.songs.SongsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private MenuItem previousMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addEventHandlers();
        setupViewPager();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int newItemIndex = -1;
        switch (item.getItemId()) {
            case R.id.navigation_albums:
                newItemIndex = 0;
                break;
            case R.id.navigation_artists:
                newItemIndex = 1;
                break;
            case R.id.navigation_songs:
                newItemIndex = 2;
                break;
            case R.id.navigation_settings:
                newItemIndex = 3;
        }
        if (viewPager.getCurrentItem() != newItemIndex) {
            viewPager.setCurrentItem(newItemIndex);
            return true;
        } else {
            return false;
        }
    }

    private void addEventHandlers() {
        navigation.setOnNavigationItemSelectedListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (previousMenuItem != null) {
                    previousMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                previousMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupViewPager() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(AlbumsFragment.newInstance(2));
        adapter.addFragment(ArtistsFragment.newInstance(2));
        adapter.addFragment(SongsFragment.newInstance(2));
        adapter.addFragment(SettingsFragment.newInstance("", ""));
        viewPager.setAdapter(adapter);
    }
}
