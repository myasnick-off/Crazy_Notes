package com.example.crazynotes.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
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

        ImageView img1 = view.findViewById(R.id.img_1);
        ImageView img2 = view.findViewById(R.id.img_2);
        ImageView img3 = view.findViewById(R.id.img_3);
        ImageView img4 = view.findViewById(R.id.img_4);
        ImageView img5 = view.findViewById(R.id.img_5);
        ImageView img6 = view.findViewById(R.id.img_6);

        // загружаем изображения по url из статического массива репозитория
        Glide.with(img1).load(FireStoreNotesRepository.IMG_URLS[0]).into(img1);
        Glide.with(img2).load(FireStoreNotesRepository.IMG_URLS[1]).into(img2);
        Glide.with(img3).load(FireStoreNotesRepository.IMG_URLS[2]).into(img3);
        Glide.with(img4).load(FireStoreNotesRepository.IMG_URLS[3]).into(img4);
        Glide.with(img5).load(FireStoreNotesRepository.IMG_URLS[4]).into(img5);
        Glide.with(img6).load(FireStoreNotesRepository.IMG_URLS[5]).into(img6);

        //Обработка нажатий по каждой из картинок (потом заменить на RecyclerView!)
        img1.setOnClickListener(v -> {
            imgUrl = FireStoreNotesRepository.IMG_URLS[0];
            imgPublish();
        });

        img2.setOnClickListener(v -> {
            imgUrl = FireStoreNotesRepository.IMG_URLS[1];
            imgPublish();
        });

        img3.setOnClickListener(v -> {
            imgUrl = FireStoreNotesRepository.IMG_URLS[2];
            imgPublish();
        });

        img4.setOnClickListener(v -> {
            imgUrl = FireStoreNotesRepository.IMG_URLS[3];
            imgPublish();
        });

        img5.setOnClickListener(v -> {
            imgUrl = FireStoreNotesRepository.IMG_URLS[4];
            imgPublish();
        });

        img6.setOnClickListener(v -> {
            imgUrl = FireStoreNotesRepository.IMG_URLS[5];
            imgPublish();
        });
    }

    private void imgPublish() {
        Bundle result = new Bundle();
        result.putString(ARG_IMG, imgUrl);
        getParentFragmentManager().setFragmentResult(KEY_IMG, result);
        dismiss();
    }
}
