package com.example.crazynotes.ui.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crazynotes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageDialogAdapter extends RecyclerView.Adapter<ImageDialogAdapter.ImageViewHolder> {

    private OnImageClickListener listener;
    private List<String> imgUrlList = new ArrayList<>();

    @NonNull
    @Override
    public ImageDialogAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(imageItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String url = imgUrlList.get(position);
        Glide.with(holder.getImageView()).load(url).into(holder.getImageView());
        holder.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return imgUrlList.size();
    }

    // метод передачи списка данных в адаптер
    public void setImgUrlList(String... args) {
        imgUrlList.clear();
        imgUrlList = Arrays.asList(args);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_image_view);

            // отрабатываем нажатие не элемент списка
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onImageClicked(getAdapterPosition());
                    }
                }
            });
        }

        // геттер для ImageView
        public ImageView getImageView() {
            return image;
        }
    }

    // Интерфейс для обработки событий нажатия на элементы списка
    interface OnImageClickListener {

        void onImageClicked(int imgPosition);
    }

    // геттер для listener
    OnImageClickListener getListener() {
        return listener;
    }

    // сеттер для listener
    public void setListener(ImageDialogAdapter.OnImageClickListener listener) {
        this.listener = listener;
    }
}
