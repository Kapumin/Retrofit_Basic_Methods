package com.example.learning_retrofit_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        textView = findViewById(R.id.textView);

        //-------------------------------------------------------------//

        getPost();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void getPost(){

        JsonPlaceholderAPI jsonPlaceholderAPI = MainActivity.createAPIInterface();

        Map<String,String > parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");

//        Call<List<Post>> call = jsonPlaceholderAPI.getPosts(new  Integer[]{1,2},"id","desc");
        Call<List<Post>> call = jsonPlaceholderAPI.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    textView.setText("Code : "+response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts){
                    String content  ="";
                    content += "ID : "+post.getId() +"\n\n";
                    content += "userID : "+post.getUserId() +"\n\n";
                    content += "title : "+post.getTitle() +"\n\n";
                    content += "body : "+post.getText() +"\n\n\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

}