package com.example.teamworks.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teamworks.R;
import com.example.teamworks.SignIn;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {

    FirebaseUser user;
    String uid,email;
    TextView Email;
    Button LogOut;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate( R.layout.bottom_sheet,container , false );

        Email = v.findViewById( R.id.userEmail );
        LogOut = v.findViewById( R.id.logout );
        firebaseAuth = FirebaseAuth.getInstance();

        getEmail();

        LogOut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);
            }
        } );

            return v;

    }

    private void getEmail() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        FirebaseDatabase.getInstance().getReference().child( uid ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                email = dataSnapshot1.child( "userEmail" ).getValue(String.class);

                setEmail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    private void setEmail() {
        Email.setText( email );

    }
}

