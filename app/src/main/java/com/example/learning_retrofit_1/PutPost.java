package com.example.learning_retrofit_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PutPost extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_post);

        textView =findViewById(R.id.textView);

        putPost();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void putPost(){
        Post post = new Post(11,null,"I m Noob");



        JsonPlaceholderAPI jsonPlaceholderAPI = MainActivity.createAPIInterface();

        Call<Post> call = jsonPlaceholderAPI.putPost("Hello",1,post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textView.setText("Code : " +response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code : "+ response.code()+"\n\n";
                content += "Id : "+ postResponse.getId()+"\n\n";
                content += "userId : "+ postResponse.getUserId()+"\n\n";
                content += "title : "+ postResponse.getTitle()+"\n\n";
                content += "Body "+ postResponse.getText()+"\n\n\n\n";

                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

}