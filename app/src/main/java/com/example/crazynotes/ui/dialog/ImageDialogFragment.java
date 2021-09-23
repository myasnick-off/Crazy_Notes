package com.example.crazynotes.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.FireStoreNotesRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImageDialogFragment extends BottomSheetDialogFragment {

    public static final String ARG_IMG = "ARG_IMG";
    public static final String KEY_IMG = "KEY_IMG";

    private String imgUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageDialogAdapter adapter = new ImageDialogAdapter();
        adapter.setImgUrlList(FireStoreNotesRepository.IMG_URLS);

        RecyclerView imageList = view.findViewById(R.id.image_dialog_recycler);
        imageList.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        imageList.setAdapter(adapter);

        adapter.setListener(new ImageDialogAdapter.OnImageClickListener() {
            @Override
            public void onImageClicked(int imgPosition) {
                imgUrl = FireStoreNotesRepository.IMG_URLS[imgPosition];
                imgPublish();
            }
        });
    }

    private void imgPublish() {
        Bundle result = new Bundle();
        result.putString(ARG_IMG, imgUrl);
        getParentFragmentManager().setFragmentResult(KEY_IMG, result);
        dismiss();
    }
}
