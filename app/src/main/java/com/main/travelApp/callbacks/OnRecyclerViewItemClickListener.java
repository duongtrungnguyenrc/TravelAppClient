package com.main.travelApp.callbacks;

public interface OnRecyclerViewItemClickListener<T> {
    void onClick(T item, long position);
}
