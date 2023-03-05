package android.bignerdranch.myapplication.ApiAbout;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    //登录验证
    @POST("login")
    @FormUrlEncoded
    Call<SimpleResult> loginTest(@Field("username")String username, @Field("password")String password);

    //token验证
    @POST("token_verify")
    Call<SimpleResult> tokenVerify(@Header("Authorization")String token);

    //发布帖子
    @POST("post")
    @FormUrlEncoded
    Call<SimpleResult> publishPosts(@Header("Authorization")String token, @Query("file_have")String file_have,
                                    @Field("type")String type, @Field("title")String title, @Field("content")String content);
    //我的帖子
    @GET("user/my_post")
    Call<SimpleResult> myPost(@Header("Authorization")String token);

    //修改个人信息
    @PUT("user/my_msg")
    @FormUrlEncoded
    Call<SimpleResult> putMyMsg(@Header("Authorization")String token, @Field("gender")String gender,
                                @Field("name")String name, @Field("signature")String signature);
    //查询自己信息
    @GET("user/my_outline")
    Call<ComplexResult> getMyMsg(@Header("Authorization")String token);

    //查询他人信息
    @GET("user/{id}/user_outline")
    Call<ComplexResult> getUserMsg(@Path("id")String userId,@Header("Authorization")String token);

    //查询某个帖子
    @GET("post/{id}")
    Call<ComplexResult> seekPosts(@Path("id")String id, @Header("Authorization")String token);
}
