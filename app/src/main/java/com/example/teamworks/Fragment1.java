package com.example.teamworks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.teamworks.ui.main.ExampleBottomSheetDialog;

public class Fragment1 extends Fragment {

    LinearLayout phone_number = null;
    LinearLayout email = null;
    ImageView ham;
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment1_layout,container,false );

        phone_number = view.findViewById( R.id.phone_number );
        email = view.findViewById( R.id.email );
        ham = view.findViewById( R.id.ham );

        buttonsClick();

        return view;


    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    private void buttonsClick() {

        ham.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();
                bottomSheet.show(myContext.getSupportFragmentManager(), "exampleBottomSheet");
            }
        } );

        phone_number.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "+91 9123456789"));
                getActivity().startActivity( intent );
            }
        } );

        email.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String x = "abcde@xyz.com";
                Intent emailIntent = new Intent( Intent.ACTION_SEND );
                emailIntent.setData( Uri.parse( "mailto:" ) );
                emailIntent.setType( "text/plain" );
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{x});

                try {
                    getActivity().startActivity( Intent.createChooser( emailIntent, "Email" ) );

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } );
    }
}
