package com.example.crazynotes.ui.dialog;

import com.example.crazynotes.domain.Callback;
import com.example.crazynotes.domain.ImagesRepository;

import java.util.ArrayList;
import java.util.List;

public class ImageDialogPresenter {

    private final ImageDialogView dialogView;
    private final ImagesRepository repository;
    private List<String> imageList;


    public ImageDialogPresenter(ImageDialogView dialogView, ImagesRepository repository) {
        this.dialogView = dialogView;
        this.repository = repository;
    }

    // метод запрашивает список с url картинок из репозитория и возвращает его во вьюху
    public void imgUrlListRequest() {
        dialogView.showProgress();
        repository.getImageUrls(new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                dialogView.hideProgress();
                imageList = new ArrayList<>();
                imageList.addAll(result);
                dialogView.showImages(result);
            }
        });
    }
}
