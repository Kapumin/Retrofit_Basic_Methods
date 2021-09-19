package com.example.learning_retrofit_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatchActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch);

        textView = findViewById(R.id.textView);

        PatchPost();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void PatchPost(){
        Post post = new Post(11,null,"I m Noob");

        Map<String,String> headers = new HashMap<>();
        headers.put("Map-Header1","Hello");
        headers.put("Map-Header2","I am a Noob Programmer");


        JsonPlaceholderAPI jsonPlaceholderAPI = MainActivity.createAPIInterface();

        Call<Post> call = jsonPlaceholderAPI.patchPost(headers,1,post);

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
                content += "UserId : "+ postResponse.getUserId()+"\n\n";
                content += "Title : "+ postResponse.getTitle()+"\n\n";
                content += "Body "+ postResponse.getText()+"\n\n\n\n";

                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

}