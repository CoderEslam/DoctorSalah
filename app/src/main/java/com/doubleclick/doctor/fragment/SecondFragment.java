package com.doubleclick.doctor.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.doctor.Adapters.SecondAdapter;
import com.doubleclick.doctor.Interface.SecondInterface;
import com.doubleclick.doctor.R;
import com.doubleclick.doctor.model.Sub;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment implements SecondInterface {



    private RecyclerView rv_second;
    private DatabaseReference reference;
    private SecondAdapter secondAdapter;
    ArrayList<Sub> arrayListSubs = new ArrayList<>();


    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
        rv_second = view.findViewById(R.id.rv_second);
        secondAdapter = new SecondAdapter(arrayListSubs, this);
        rv_second.setAdapter(secondAdapter);
        reference.child("Subs").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListSubs.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Sub sub = dataSnapshot.getValue(Sub.class);
                    assert getArguments() != null;
                    assert sub != null;
                    if (sub.getNameParent().equals(SecondFragmentArgs.fromBundle(getArguments()).getId())) {
                        arrayListSubs.add(sub);
                    }
                }
                secondAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemSecond(String pos) {
        Navigation.findNavController(requireActivity(), R.id.nav_home_fragment).navigate(SecondFragmentDirections.actionFragmentSecondToFragmentTherd(pos));
    }
}