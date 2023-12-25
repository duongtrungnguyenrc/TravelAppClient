package com.main.travelApp.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.main.travelApp.R;
import com.main.travelApp.adapters.TourListExploreAdapter;
import com.main.travelApp.databinding.FragmentExploreBinding;
import com.main.travelApp.request.TourFilterRequest;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.ui.components.DatePicker;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.viewmodels.TourViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExploreFragment extends Fragment {
    private TourListExploreAdapter tourAdapter;
    private FragmentExploreBinding exploreBinding;
    private TourViewModel tourViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exploreBinding = FragmentExploreBinding.inflate(inflater, container, false);

        return exploreBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tourViewModel = new ViewModelProvider(requireActivity()).get(TourViewModel.class);
        init(view);
        setEvents();
    }

    private void init(View view){
        tourAdapter = new TourListExploreAdapter(getActivity());
        tourViewModel.getTours()
                .observe(getViewLifecycleOwner(), tours -> {
                    tourAdapter.setTours(tours.getTours());
                });

        exploreBinding.rcvExploreTours.setLayoutManager(new GridLayoutManager(getContext(), 2));
        exploreBinding.rcvExploreTours.setAdapter(tourAdapter);
    }
    private void setEvents(){
        exploreBinding.btnNextPage.setOnClickListener(onClickListener());
        exploreBinding.btnPrevPage.setOnClickListener(onClickListener());
        exploreBinding.btnFilter.setOnClickListener(onClickListener());
    }

    private View.OnClickListener onClickListener() {
        return view -> {
            if (view == exploreBinding.btnNextPage) {
                if (tourViewModel.getPage() < tourViewModel.getTours().getValue().getPages()) {
                    tourViewModel.setPage(tourViewModel.getPage() + 1);
                }
            } else if (view == exploreBinding.btnPrevPage) {
                if (tourViewModel.getPage() > 1) {
                    tourViewModel.setPage(tourViewModel.getPage() - 1);
                }
            } else if (view == exploreBinding.btnFilter) {
                MyDialog myDialog = new MyDialog(getContext(), getLayoutInflater(), R.layout.fragment_filter, new MyDialog.Handler() {
                    @Override
                    public void handle(AlertDialog dialog, View contentView) {
                        EditText departDate = contentView.findViewById(R.id.edtDepartDate);
                        EditText endDate = contentView.findViewById(R.id.edtEndDate);
                        EditText fromPrice = contentView.findViewById(R.id.edtFromPrice);
                        EditText toPrice = contentView.findViewById(R.id.edtToPrice);
                        RatingBar rb = contentView.findViewById(R.id.rbTourRating);
                        Button btnCancel = contentView.findViewById(R.id.btnCancel);
                        Button btnSubmit = contentView.findViewById(R.id.btnSubmit);
                        RadioGroup rg = (RadioGroup) contentView.findViewById(R.id.rdgType);
                        final String[] type = {""};
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                              @Override
                              public void onCheckedChanged(RadioGroup group, int checkedId)
                              {
                                  type[0] = ((RadioButton) contentView.findViewById(checkedId)).getText().toString();
                              }
                          }
                        );

                        btnCancel.setOnClickListener(v -> {
                            dialog.dismiss();
                            tourViewModel.getToursInit().observe(getViewLifecycleOwner(), data -> {
                                tourAdapter.setTours(data.getTours());
                            });
                        });


                        btnSubmit.setOnClickListener(v -> {
                            TourFilterRequest request = new TourFilterRequest();
                            request.setDepartDate(departDate.getText().toString().isEmpty() ? null : departDate.getText().toString());
                            request.setEndDate(endDate.getText().toString().isEmpty() ? null : endDate.getText().toString());
                            request.setStar((int) rb.getRating() == 0 ? null : (int) rb.getRating());
                            request.setFromPrice(fromPrice.getText().toString().isEmpty() ? null : Double.parseDouble(fromPrice.getText().toString()));
                            request.setToPrice(toPrice.getText().toString().isEmpty() ? null : Double.parseDouble(toPrice.getText().toString()));
                            request.setType(type[0].isEmpty() ? null : type[0]);
                            tourViewModel.filter(request);
                            dialog.dismiss();
                        });
                        departDate.setOnClickListener(view -> {
                            DatePicker datePicker = new DatePicker();
                            datePicker.setLister(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                                    Calendar mCalendar = Calendar.getInstance();
                                    mCalendar.set(Calendar.YEAR, i);
                                    mCalendar.set(Calendar.MONTH, i1);
                                    mCalendar.set(Calendar.DAY_OF_MONTH, i2);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
                                    String selectedDate = dateFormat.format(mCalendar.getTime());
                                    departDate.setText(selectedDate);
                                }
                            });
                            datePicker.show(getParentFragmentManager(), "DATE_PICKER");
                        });

                        endDate.setOnClickListener(view -> {
                            DatePicker datePicker = new DatePicker();
                            datePicker.setLister(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                                    Calendar mCalendar = Calendar.getInstance();
                                    mCalendar.set(Calendar.YEAR, i);
                                    mCalendar.set(Calendar.MONTH, i1);
                                    mCalendar.set(Calendar.DAY_OF_MONTH, i2);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
                                    String selectedDate = dateFormat.format(mCalendar.getTime());
                                    endDate.setText(selectedDate);
                                }
                            });
                            datePicker.show(getParentFragmentManager(), "DATE_PICKER");
                        });

                    }
                });
                myDialog.show(getParentFragmentManager(), "FIlTER_DIALOG");
            }
        };
    }
}