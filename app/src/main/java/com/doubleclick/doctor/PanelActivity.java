package com.doubleclick.doctor;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.doubleclick.doctor.model.F1;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PanelActivity extends AppCompatActivity {

    private final int IMAGE_REQUEST = 100;
    private Uri uri = null;
    private ImageView image;
    private ImageView image_upload;
    private CardView card_ask_photo;
    private Button submit, submit_sub;
    private String menuOptionItemSelected;
    private SmartMaterialSpinner<String> _spinnerMenu_;
    private EditText _title_sub_, title;

    private List<String> menuOption = new ArrayList<>();


    private static final String TAG = "PanelActivity";
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        reference = FirebaseDatabase.getInstance().getReference();
        image = findViewById(R.id.image);
        image_upload = findViewById(R.id.image_upload);
        card_ask_photo = findViewById(R.id.card_ask_photo);
        _title_sub_ = findViewById(R.id._title_sub_);
        title = findViewById(R.id.title);
        submit = findViewById(R.id.submit);
        submit_sub = findViewById(R.id.submit_sub);
        _spinnerMenu_ = findViewById(R.id._spinnerMenu_);

        card_ask_photo.setOnClickListener(view -> {
            openImage();
        });

        submit.setOnClickListener(view -> {
            uploadFirst();
        });
        submit_sub.setOnClickListener(view -> {
            uploadImage();
        });

        spinnerScreen2();


    }

    private void spinnerScreen2() {

        reference.child("F1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    F1 f1 = dataSnapshot.getValue(F1.class);
                    assert f1 != null;
                    menuOption.add(f1.getF1());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(PanelActivity.this, android.R.layout.simple_spinner_item, menuOption);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                _spinnerMenu_.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                _spinnerMenu_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        menuOptionItemSelected = menuOption.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        menuOptionItemSelected = menuOption.get(0);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadFirst() {
        HashMap<String, Object> map = new HashMap<>();
        String id = reference.push().getKey() + System.currentTimeMillis();
        map.put("F1", title.getText().toString());
        reference.child("F1").child(id).updateChildren(map);
        title.setText("");
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }


    public void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        final StorageReference fileReference = FirebaseStorage.getInstance()
                .getReference("IMAGES").child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> url = taskSnapshot.getStorage().getDownloadUrl();
            url.addOnCompleteListener(task -> {
                if (!_title_sub_.getText().toString().isEmpty() && !menuOptionItemSelected.isEmpty()) {
                    HashMap<String, Object> map = new HashMap<>();
                    String id = reference.push().getKey() + System.currentTimeMillis();
                    map.put("image", task.getResult().toString());
                    map.put("name", _title_sub_.getText().toString());
                    map.put("nameParent", menuOptionItemSelected);
                    map.put("id", id);
                    reference.child("Subs").child(id).updateChildren(map);
                    _title_sub_.setText("");
                }
                pd.dismiss();
            });
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            uri = Uri.parse(data.getData().toString());
            image.setImageURI(uri);
        }
    }
}
