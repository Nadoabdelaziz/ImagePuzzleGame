package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPhotos {
    Context context;
    public String []newphotos;
    Retrofit retrofit = new Retrofit.Builder().baseUrl(JsonPlaceHolderApi.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
    Call<ResponseBody> call2 = jsonPlaceHolderApi.getRestaurantsBySearch("landscape","25","Ai36lJz0buf2dphMxzpFWQ1SXLXLbl4D1MeZsC5c77dDrcij6JOCI70o");

    public GetPhotos(Context context){
        this.context = context;
    }
    public String[] getphotos() {

        newphotos = new String[25];
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        JSONObject json = new JSONObject(response.body().string());
//                        String pageName = json.getJSONArray("photos").getJSONObject(1).getJSONObject("src").getString("original");

                        for (int i = 0;i<25;i++){
                            newphotos[i]=json.getJSONArray("photos").getJSONObject(i).getJSONObject("src").getString("original");
//                            Log.d("GET", "onResponse: "+json.getJSONArray("photos").getJSONObject(i).getJSONObject("src").getString("original")+"\n");
                        }
                        Log.d("GET", "onResponse: "+ Arrays.toString(newphotos));

                        SharedPreferences prefs = context.getSharedPreferences("px_photos", 0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("newphotos" +"_size", newphotos.length);
                        for(int i=0;i<newphotos.length;i++)
                            editor.putString("newphotos" + "_" + i, newphotos[i]);
                        editor.commit();


//                        newphotos(json.getJSONArray("photos").getJSONObject(i).getJSONObject("src").getString("original"));

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("GET", "onResponse: unsuccsfull" + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("GET", "onFailure: " + t.toString());
            }
        });
        return newphotos;
    }

//    public void newphotos(){
//
//    }
}

