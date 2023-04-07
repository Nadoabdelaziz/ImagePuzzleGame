package nadadev.com.jigsawpuzzleimgGame;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    String BaseUrl = "https://api.pexels.com/";
    String query = "landscape";
    String per_page = "5";
    @GET("posts")
    @Headers({
            "Authorization: Ai36lJz0buf2dphMxzpFWQ1SXLXLbl4D1MeZsC5c77dDrcij6JOCI70o"
    })
    Call<List<Post>> getPosts();

    @GET("v1/search")
    Call<ResponseBody> getRestaurantsBySearch(@Query("query") String query_id,
                                              @Query("per_page") String per_page,
                                              @Header("Authorization") String apiKey);
}
