package gfandos.myplaces.Fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import gfandos.myplaces.Activities.Information;
import gfandos.myplaces.Activities.MainActivity;
import gfandos.myplaces.MyPlaces;
import gfandos.myplaces.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class InformationFragment extends Fragment {

    private RadioGroup radioGroup;

    private RadioButton radioRes;
    private RadioButton RadioMon;
    private RadioButton RadioOth;

    private EditText titleEdit;
    private EditText descriptionEdit;

    private Button onSave;

    private String type;
    private String title;
    private String description;

    public InformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyPlaces my = MyPlaces.getInstance();


        final String absolutePath = my.filePhoto.getAbsolutePath();

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        radioRes = (RadioButton) view.findViewById(R.id.radioRes);
        RadioMon = (RadioButton) view.findViewById(R.id.RadioMon);
        RadioOth = (RadioButton) view.findViewById(R.id.RadioOth);

        titleEdit = (EditText) view.findViewById(R.id.titleEdit);
        descriptionEdit = (EditText) view.findViewById(R.id.descriptionEdit);

        onSave = (Button) view.findViewById(R.id.onSave);

        onSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioRes.isChecked()) {
                    type = radioRes.getText().toString();
                } else if (RadioMon.isChecked()) {
                    type = RadioMon.getText().toString();
                } else if (RadioOth.isChecked()) {
                    type = RadioOth.getText().toString();
                }

                title = titleEdit.getText().toString();
                description = descriptionEdit.getText().toString();

                String data = type + " " + title + " " + description+ " "+absolutePath;

                Intent i = new Intent(data);

                getActivity().setResult(404, i);
                getActivity().finish();


//                ((MainActivity)getActivity()).pushToDB(data);
                //((Information)getActivity()).finish();

            }
        });
    }
}
