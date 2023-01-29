package com.doubleclick.doctor.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.doctor.Adapters.FirstAdapter;
import com.doubleclick.doctor.Interface.FirstInterface;
import com.doubleclick.doctor.PanelActivity;
import com.doubleclick.doctor.R;
import com.doubleclick.doctor.model.F1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FristFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FristFragment extends Fragment implements FirstInterface {


    private DatabaseReference reference;
    ArrayList<String> arrayList = new ArrayList<>();
    private FirstAdapter firstAdapter;

    private RecyclerView rv_first;

    public FristFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FristFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FristFragment newInstance(String param1, String param2) {
        FristFragment fragment = new FristFragment();
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
        return inflater.inflate(R.layout.fragment_frist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
        rv_first = view.findViewById(R.id.rv_first);
        firstAdapter = new FirstAdapter(arrayList, FristFragment.this);
        reference.child("F1").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    F1 f1 = dataSnapshot.getValue(F1.class);
                    assert f1 != null;
                    arrayList.add(f1.getF1());
                    firstAdapter.notifyDataSetChanged();
                    rv_first.setAdapter(firstAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        view.findViewById(R.id.addConstraintLayout).setOnLongClickListener(view1 -> {
            startActivity(new Intent(requireActivity(), PanelActivity.class));
            return true;
        });

    }

    @Override
    public void onItemFirst(String pos) {
        Navigation.findNavController(requireActivity(), R.id.nav_home_fragment).navigate(FristFragmentDirections.actionFragmentFristToFragmentSecond(pos));
    }
}