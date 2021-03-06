package com.placediscovery.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.placediscovery.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMeetupFragment extends Fragment implements View.OnClickListener {

    EditText meetUpName;
    EditText meetUpDescription;
    EditText dateSelect, timeSelect;
    Button button;
    private int hour;
    private int min;


    public CreateMeetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_meetup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meetUpName = (EditText) view.findViewById(R.id.meetup_name);
        meetUpDescription = (EditText) view.findViewById(R.id.meetup_description);
        dateSelect = (EditText) view.findViewById(R.id.date_select);
        dateSelect.setOnClickListener(this);
        dateSelect.setInputType(InputType.TYPE_NULL);
        dateSelect.setOnClickListener(this);
        timeSelect = (EditText) view.findViewById(R.id.time_select);
        timeSelect.setInputType(InputType.TYPE_NULL);
        timeSelect.setOnClickListener(this);
        button = (Button) view.findViewById(R.id.btn_submit);
        button.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Fragment newFragment;
        switch (v.getId()) {
            case R.id.time_select:
                newFragment = new TimePickerFragment();
                ((TimePickerFragment) newFragment).show(getFragmentManager(), "timePicker");
                break;
            case R.id.date_select:
                newFragment = new DatePickerFragment();
                ((DatePickerFragment) newFragment).show(getFragmentManager(), "Datepicker");
                break;
        }
    }
}
