package com.example.kiu.hubforgithub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kiu.hubforgithub.adapter.GitHubRepoAdapter;
import com.example.kiu.hubforgithub.api.GitHubClient;
import com.example.kiu.hubforgithub.api.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView repoList;
    private String githubAPI = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repoList = findViewById(R.id.repoListView);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(githubAPI)
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = builder.build();

        GitHubClient client = retrofit.create(GitHubClient.class);
        Call<List<GitHubRepo>> call = client.repoForUser("bamakant");

        call.enqueue(new Callback<List<GitHubRepo>>() {

            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
               List<GitHubRepo> repos = response.body();

               repoList.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));

            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error :(",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
