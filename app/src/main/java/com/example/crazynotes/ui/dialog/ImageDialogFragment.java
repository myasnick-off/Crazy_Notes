package com.example.crazynotes.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.FireStoreNotesRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ImageDialogFragment extends BottomSheetDialogFragment implements ImageDialogView {

    public static final String ARG_IMG = "ARG_IMG";
    public static final String KEY_IMG = "KEY_IMG";

    private List<String> imageList;
    private ProgressBar imgProgress;
    private ImageDialogAdapter adapter;
    private ImageDialogPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ImageDialogPresenter(this, new FireStoreNotesRepository());
        adapter = new ImageDialogAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgProgress = view.findViewById(R.id.img_progress);
        // запрашиваем у презентера список с url картинок
        presenter.imgUrlListRequest();

        RecyclerView imageRecycler = view.findViewById(R.id.image_dialog_recycler);
        imageRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        imageRecycler.setAdapter(adapter);

        adapter.setListener(new ImageDialogAdapter.OnImageClickListener() {
            @Override
            public void onImageClicked(int imgPosition) {
                String imgUrl = imageList.get(imgPosition);
                imgPublish(imgUrl);
                dismiss();
            }
        });
    }

    private void imgPublish(String url) {
        Bundle result = new Bundle();
        result.putString(ARG_IMG, url);
        getParentFragmentManager().setFragmentResult(KEY_IMG, result);
    }

    @Override
    public void showImages(List<String> imgUrlList) {
        imageList = new ArrayList<>();
        imageList.addAll(imgUrlList);
        adapter.setImgUrlList(imageList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        imgProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        imgProgress.setVisibility(View.GONE);
    }
}
