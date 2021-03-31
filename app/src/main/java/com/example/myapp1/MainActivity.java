package com.example.myapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView MrecyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MrecyclerView= findViewById(R.id.recyclerview_video);
        MrecyclerView.setHasFixedSize(true);
        MrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        reference=database.getReference("video");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<member> options = new FirebaseRecyclerOptions.Builder<member>()
                .setQuery(reference, member.class)
                .build();

        FirebaseRecyclerAdapter<member, viewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<member, viewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull member model) {
                        holder.setVideo(getApplication(), model.getTitle(),model.getUrl());
                    }

                    @NonNull
                    @Override
                    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                        return new viewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        MrecyclerView.setAdapter(firebaseRecyclerAdapter);


    }

}
