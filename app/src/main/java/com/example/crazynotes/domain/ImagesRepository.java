package com.example.crazynotes.domain;

import java.util.List;

public interface ImagesRepository {

    void getImageUrls(Callback<List<String>> callback);
}
