package android.bignerdranch.myapplication.ApiAbout;

import android.bignerdranch.myapplication.ui.home.Posts;
import android.database.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    //登录验证
    @POST("login")
    @FormUrlEncoded
    Call<ApiResult> loginTest(@Field("username")String username, @Field("password")String password);

    //发布帖子
    @POST("post")
    @FormUrlEncoded
    Call<ApiResult> publishPosts(@Header("Authorization")String token, @Query("file_have")String file_have,
                                 @Field("type")String type, @Field("title")String title, @Field("content")String content);

    //查询某个帖子
    @GET("post/{id}")
    Call<PostsResult> seekPosts(@Path("id")String id, @Header("Authorization")String token);

    //token验证
    @POST("token_verify")
    Call<ApiResult> tokenVerify(@Header("Authorization")String token);

    //我的帖子
    @GET("user/my_post")
    Call<ApiResult> myPost(@Header("Authorization")String token);
}
