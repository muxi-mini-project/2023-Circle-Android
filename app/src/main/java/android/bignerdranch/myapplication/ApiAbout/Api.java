package android.bignerdranch.myapplication.ApiAbout;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    //登陆
    @POST("login")
    @FormUrlEncoded
    Call<SignInResult> loginTest(@Field("username")String username, @Field("password")String password);

    //发帖子
    @POST("post")
    @FormUrlEncoded
    Call<SignInResult> publishPosts(@Header("Authorization")String token, @Query("file_have")String file_have,
                                 @Field("type")String type,@Field("title")String title,@Field("content")String content);

    //用帖子id查帖子
    @GET("post/{id}")
    Call<SignInResult> seekPosts(@Path("id")int id,@Header("Authorization")String token);
}
