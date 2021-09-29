package com.example.crazynotes.ui.dialog;

import java.util.List;

public interface ImageDialogView {

    void showImages(List<String> imgUrlList);

    void showProgress();

    void hideProgress();
}
