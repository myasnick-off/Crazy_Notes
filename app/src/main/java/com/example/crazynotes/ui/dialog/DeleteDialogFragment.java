package com.example.crazynotes.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.crazynotes.R;

public class DeleteDialogFragment extends DialogFragment {

    public static final String KEY_DEL_DIALOG = "KEY_DEL_DIALOG";
    public static final String ARG_OK = "ARG_OK";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // обработчик нажатия на кнопки диалогового окна
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle result = new Bundle();
                result.putBoolean(ARG_OK, true);
                getParentFragmentManager().setFragmentResult(KEY_DEL_DIALOG, result);
            }
        };

        // создаем непосредственно диалоговое окно
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.warning)
                .setMessage(R.string.delete_message)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setPositiveButton(R.string.yes, dialogListener)
                .setNegativeButton(R.string.no, null)
                .create();
    }
}
