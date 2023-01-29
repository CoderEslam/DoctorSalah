package com.doubleclick.doctor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.doubleclick.doctor.R;
import com.doubleclick.doctor.model.Description;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link therdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class therdFragment extends Fragment {


    private WebView describtion;
    private TextView title_;

    private DatabaseReference reference;

    private static final String TAG = "therdFragment";


    public therdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment therdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static therdFragment newInstance(String param1, String param2) {
        therdFragment fragment = new therdFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_therd, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
        describtion = view.findViewById(R.id.describtion);
        title_ = view.findViewById(R.id.title_);

        reference.child("Description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Description description = dataSnapshot.getValue(Description.class);
                    assert getArguments() != null;
                    assert description != null;
                    if (description.getSub_parent().equals(therdFragmentArgs.fromBundle(getArguments()).getName())) {
                        describtion.loadDataWithBaseURL(
                                null,
                                description.getSub_description(),
                                "text/html",
                                "utf-8",
                                null
                        );
                        title_.setText(description.getSub_title());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}