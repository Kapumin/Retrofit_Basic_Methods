package com.example.learning_retrofit_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        textView = findViewById(R.id.textView);

        createPost();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    public void createPost(){

        JsonPlaceholderAPI jsonPlaceholderAPI = MainActivity.createAPIInterface();
//        Post post =  new Post(48,"Manga","Manga is a good Japanese comic.");
        Map<String,String> fields = new HashMap<>();

        fields.put("userId","84");
        fields.put("title","Nakano Nino");
//        fields.put("body","Waifu");


        Call<Post> call =  jsonPlaceholderAPI.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textView.setText("Code : "+ response.code());
                    return;
                }

                Post postResponse = response.body();

                String content  ="";
                content += "Code : "+response.code()+"\n\n";
                content += "ID : "+ postResponse.getId() +"\n\n";
                content += "userID :"+ postResponse.getUserId() +"\n\n";
                content += "title : " +postResponse.getTitle() +"\n\n";
                content += "body :" +postResponse.getText() +"\n\n\n\n";

                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}