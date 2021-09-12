package com.example.crazynotes.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.ui.menu.SettingsFragment;
import com.example.crazynotes.ui.menu.HelpFragment;
import com.example.crazynotes.ui.menu.AboutFragment;
import com.example.crazynotes.ui.details.NoteContentActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnNoteClicked {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Text submitted", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_tool_bar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.drawer_settings) {

                    // очищаем бэкстек перед запуском нового фрагмента
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new SettingsFragment(), "settingsFrag")
                            .addToBackStack(null)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (item.getItemId() == R.id.drawer_help) {

                    // очищаем бэкстек перед запуском нового фрагмента
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new HelpFragment(), "helpFrag")
                            .addToBackStack(null)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (item.getItemId() == R.id.drawer_About) {

                    // очищаем бэкстек перед запуском нового фрагмента
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new AboutFragment(), "aboutFrag")
                            .addToBackStack(null)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void noteOnClicked(Note note) {
        if (!getResources().getBoolean(R.bool.isLandscape)) {
            Intent intent = new Intent(this, NoteContentActivity.class);
            intent.putExtra(NoteContentActivity.KEY_NOTE_CONTENT, note);
            startActivity(intent);
        }
    }
}