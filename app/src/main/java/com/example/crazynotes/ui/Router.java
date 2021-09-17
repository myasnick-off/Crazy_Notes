package com.example.crazynotes.ui;

import androidx.fragment.app.FragmentManager;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.ui.details.NoteContentFragment;
import com.example.crazynotes.ui.details.NoteEditFragment;
import com.example.crazynotes.ui.list.NotesListFragment;
import com.example.crazynotes.ui.menu.AboutFragment;
import com.example.crazynotes.ui.menu.HelpFragment;
import com.example.crazynotes.ui.menu.SettingsFragment;

public class Router {

    FragmentManager fragmentManager;

    public Router(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotesList() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new NotesListFragment())
                .commit();
    }

    public void showNoteContent(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NoteContentFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }

    public void showNoteEdit(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NoteEditFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }

    public void showSettings() {
        // очищаем бэкстек перед запуском нового фрагмента
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_main, new SettingsFragment(), "settingsFrag")
                .addToBackStack(null)
                .commit();
    }

    public void showHelp() {
        // очищаем бэкстек перед запуском нового фрагмента
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_main, new HelpFragment(), "helpFrag")
                .addToBackStack(null)
                .commit();
    }

    public void showAbout() {
        // очищаем бэкстек перед запуском нового фрагмента
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_main, new AboutFragment(), "aboutFrag")
                .addToBackStack(null)
                .commit();
    }


}
