package com.example.crazynotes.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crazynotes.R;
import com.example.crazynotes.ui.list.NotesListFragment;

public class MainFragment extends Fragment implements RouterHolder, BackPressedMonitor {

    Router router;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public Router getRouter() {
        return router;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        router = new Router(getChildFragmentManager());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            router.showNotesList();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack();
            return true;
        }
        return false;
    }
}
