package com.main.travelApp.callbacks;

public interface OnRecyclerViewHasNestedItemClickListener <T, K>{
    void onClick(T item, long position);

    default void onBlur(T item, K nestedItem){}
}
