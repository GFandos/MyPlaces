package gfandos.myplaces.Fragments;

import android.app.Activity;
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

import gfandos.myplaces.Activities.Information;
import gfandos.myplaces.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class InformationFragment extends Fragment {

    private CheckBox checkRes;
    private CheckBox checkMon;
    private CheckBox checkOth;

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

        checkRes = (CheckBox) view.findViewById(R.id.checkRes);
        checkMon = (CheckBox) view.findViewById(R.id.checkMon);
        checkOth = (CheckBox) view.findViewById(R.id.checkOth);

        titleEdit = (EditText) view.findViewById(R.id.titleEdit);
        descriptionEdit = (EditText) view.findViewById(R.id.descriptionEdit);

        if(checkRes.isChecked()) {
            checkMon.setChecked(false);
            checkOth.setChecked(false);
            type = checkRes.getText().toString();
        } else if (checkMon.isChecked()) {
            checkRes.setChecked(false);
            checkOth.setChecked(false);
            type = checkMon.getText().toString();
        } else if (checkOth.isChecked()) {
            checkRes.setChecked(false);
            checkMon.setChecked(false);
            type = checkOth.getText().toString();
        }

        title = titleEdit.getText().toString();
        description = descriptionEdit.getText().toString();

        onSave = (Button) view.findViewById(R.id.onSave);

        onSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = type + " " + title + " " + description;

                Intent i = new Intent(data);

                ((Information)getActivity()).setResult(1, i);
                ((Information)getActivity()).finish();

            }
        });
    }
}
