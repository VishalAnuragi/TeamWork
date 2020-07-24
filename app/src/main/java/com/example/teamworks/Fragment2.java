package com.example.teamworks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class Fragment2 extends Fragment {

    ImageView mImageView;
    Button mChooseBtn;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment2_layout,container,false );

            mImageView = view.findViewById( R.id.image_from_gallery );
            mChooseBtn = view.findViewById( R.id.image_btn );

        imageButtonsListener();

    return view;
    }

    private void imageButtonsListener() {

        mChooseBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission( getContext(), Manifest.permission.READ_EXTERNAL_STORAGE )
                            == PackageManager.PERMISSION_DENIED){

                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions( permissions, PERMISSION_CODE );

                    }else {
                        pickImageFromGallery();

                    }
                }else{
                    pickImageFromGallery();

                }
            }
        } );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else {
                    Toast.makeText( getContext() , "Permission Denied..." , Toast.LENGTH_SHORT ).show();
                }
            }
        }
    }

    private void pickImageFromGallery() {

        Intent Iintent = new Intent( Intent.ACTION_PICK );
        Iintent.setType( "image/*" );
        getActivity().startActivityForResult(Iintent , IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
        mImageView.setImageURI( data.getData() );
        mChooseBtn.setVisibility(   View.INVISIBLE );
        }




    }
}
