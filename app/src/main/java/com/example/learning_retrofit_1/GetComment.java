package com.example.learning_retrofit_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetComment extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_comment);


        textView =findViewById(R.id.textView);
        getComment();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void getComment(){

        JsonPlaceholderAPI jsonPlaceholderAPI = MainActivity.createAPIInterface();

        Call<List<Comment>> call = jsonPlaceholderAPI.getComments("posts/1/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    textView.setText("Code : "+response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments){
                    String content = "";
                    content += "POST-ID : "+ comment.getPostID() +"\n\n";
                    content += "ID : "+ comment.getId() +"\n\n";
                    content += "NAME : "+comment.getName() +"\n\n";
                    content += "EMAIL: "+ comment.getEmail() +"\n\n";
                    content += "BODY : "+ comment.getText() +"\n\n\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

}