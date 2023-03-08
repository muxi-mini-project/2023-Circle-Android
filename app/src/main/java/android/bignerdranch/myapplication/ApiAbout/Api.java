package android.bignerdranch.myapplication.ApiAbout;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Call<SimpleResult> publishPostsNotPic(@Header("Authorization")String token, @Query("file_have")String file_have,
                                    @Field("type")String type, @Field("title")String title, @Field("content")String content);

    //删除帖子
    @DELETE("post")
    Call<SimpleResult> delPost(@Header("Authorization")String token,@Query("post_id")String postsId);

    //查询我的帖子id数组
    @GET("user/my_post")
    Call<SimpleResult> myPost(@Header("Authorization")String token);

    //查询某个帖子
    @GET("post/{id}")
    Call<ComplexResult> seekPosts(@Path("id")String id, @Header("Authorization")String token);

    //给帖子点赞
    @POST("post/likes")
    Call<SimpleResult> likesPosts(@Query("post_id")String postsId,@Header("Authorization")String token);

    //取消点赞
    @DELETE("post/likes")
    Call<SimpleResult> deleteLikesPosts(@Query("post_id")String postsId,@Header("Authorization")String token);

    //修改个人信息
    @PUT("user/my_msg")
    @FormUrlEncoded
    Call<SimpleResult> putMyMsg(@Header("Authorization")String token, @Field("gender")String gender,
                                @Field("name")String name, @Field("signature")String signature);

    //修改头像
    @PUT("user/my_msg")
    @Multipart
    Call<SimpleResult> putMyProfile(@Header("Authorization")String token,@Query("avatar_only")String s,
                                  @Part MultipartBody.Part file);

    //查询自己信息
    @GET("user/my_outline")
    Call<ComplexResult> getMyMsg(@Header("Authorization")String token);

    //查询他人信息
    @GET("user/{id}/user_outline")
    Call<ComplexResult> getUserMsg(@Path("id")String userId,@Header("Authorization")String token);


}
