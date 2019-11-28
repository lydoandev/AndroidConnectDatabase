package com.example.androidconnectdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvUsers;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                User user1 = new User();
                user1.firstName = "Ly nè";
                db.userDao().insert(user1);

                final List<User> users = db.userDao().getAll();
//                displayUserList(users);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayUserList(users);
                    }
                });

                Log.i("ly", "Ly" + users.size());
                return null;
            }

        }.execute();
    }

    void displayUserList(List<User> users) {
        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        UserAdapter adapter = new UserAdapter();
        adapter.users = users;

        rvUsers.setAdapter(adapter);
    }
}
