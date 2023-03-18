package android.bignerdranch.myapplication.ApiAbout;

import okhttp3.MultipartBody;
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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    //登录验证
    @POST("login")
    @FormUrlEncoded
    Call<SimpleResult> loginTest(@Field("username") String username, @Field("password") String password);

    //token验证
    @POST("token_verify")
    Call<SimpleResult> tokenVerify(@Header("Authorization") String token);

    //发布帖子
    @POST("post")
    @Multipart
    Call<SimpleResult> publishPosts(@Header("Authorization") String token, @Query("file_have") String file_have,
                                          @Part MultipartBody.Part isAnonymity
                                        ,@Part MultipartBody.Part type, @Part MultipartBody.Part title, @Part MultipartBody.Part content
                                        ,@Part MultipartBody.Part file0, @Part MultipartBody.Part file1, @Part MultipartBody.Part file2
                                        ,@Part MultipartBody.Part file3, @Part MultipartBody.Part file4, @Part MultipartBody.Part file5
                                        ,@Part MultipartBody.Part file6, @Part MultipartBody.Part file7, @Part MultipartBody.Part file8);

    //删除帖子
    @DELETE("post")
    Call<SimpleResult> delPost(@Header("Authorization") String token, @Query("post_id") String postsId);

    //评论帖子
    @POST("post/comment")
    @FormUrlEncoded
    Call<SimpleResult> commentPost(@Header("Authorization") String token, @Field("post_id") String postsId, @Field("private") boolean isPrivate,
                                   @Field("status") int one, @Field("content") String content);

    //删除评论
    @DELETE("post/comment")
    Call<SimpleResult> delComment(@Header("Authorization")String token,@Query("comment_id")String commentId,@Query("post_id")String postId);

    //根据帖子id查询帖子的评论id数组
    @GET("post/comments/{post_id}")
    Call<SimpleResult> CommentOfPosts(@Header("Authorization") String token, @Path("post_id") String postId);

    //根据评论id查询评论
    @GET("post/comment")
    Call<ComplexResult> seekComment(@Header("Authorization") String token, @Query("id") String commentId);

    //查询我的帖子id数组
    @GET("user/my_post")
    Call<SimpleResult> myPost(@Header("Authorization") String token);

    //查询推荐帖子id数组
    @POST("post/recommendations")
    @FormUrlEncoded
    Call<SimpleResult> recPost(@Header("Authorization") String token, @Field("type") String type, @Field("end_time") String end_time
            , @Field("start_time") String start_time, @Field("length") int length, @Field("start_index") int index);

    //查询关注帖子id数组
    @GET("post/following")
    Call<SimpleResult> followPost(@Header("Authorization") String token);

    //查询最新帖子id数组
    @GET("post/latest")
    Call<SimpleResult> newestPost(@Header("Authorization") String token, @Query("start") int startIndex, @Query("length") int length);

    //查询搜索帖子id数组
    @GET("post/search")
    Call<SimpleResult> searchPost(@Header("Authorization") String token, @Query("query_string") String queryString);

    //查询某个帖子
    @GET("post/{id}")
    Call<ComplexResult> seekPosts(@Path("id") String id, @Header("Authorization") String token);

    //给帖子点赞
    @POST("post/likes")
    Call<SimpleResult> likesPosts(@Query("post_id") String postsId, @Header("Authorization") String token);

    //取消点赞
    @DELETE("post/likes")
    Call<SimpleResult> deleteLikesPosts(@Query("post_id") String postsId, @Header("Authorization") String token);

    //查询我被点赞的信息id数组
    @GET("user/likes_of_my_post")
    Call<SimpleResult> getMyLikedMsg(@Header("Authorization") String token);

    //根据id查询点赞信息
    @GET("post/likes")
    Call<ComplexResult> getLikeInformation(@Query("id") String id, @Header("Authorization") String token);

    //根据id关注用户
    @POST("user/following")
    Call<SimpleResult> followUser(@Header("Authorization") String token, @Query("followed_id") String userId);

    //根据id取消关注用户
    @DELETE("user/following")
    Call<SimpleResult> delFollowUser(@Header("Authorization") String token, @Query("followed_id") String userId);

    //查询用户是否已关注
    @GET("user/whether_follow")
    Call<SimpleResult> getIsFollow(@Header("Authorization") String token, @Query("user_id") String userid);

    //查询我的关注id数组
    @GET("user/my_following")
    Call<SimpleResult> getMyFollow(@Header("Authorization") String token);

    //查询他人关注id数组
    @GET("user/{id}/user_following")
    Call<SimpleResult> getUserFollow(@Path("id") String userId, @Header("Authorization") String token);

    //查询我的粉丝id数组
    @GET("user/my_followers")
    Call<SimpleResult> getMyFans(@Header("Authorization") String token);

    //查询用户粉丝id数组
    @GET("user/{id}/user_followers")
    Call<SimpleResult> getUserFans(@Path("id") String userId, @Header("Authorization") String token);

    //查询帖子是否已赞
    @GET("post/whether_like")
    Call<SimpleResult> getIsLike(@Query("post_id") String id, @Header("Authorization") String token);

    //修改个人信息
    @PUT("user/my_msg")
    @FormUrlEncoded
    Call<SimpleResult> putMyMsg(@Header("Authorization") String token, @Field("gender") String gender,
                                @Field("name") String name, @Field("signature") String signature);

    //修改头像
    @PUT("user/my_msg")
    @Multipart
    Call<SimpleResult> putMyProfile(@Header("Authorization") String token, @Query("avatar_only") String s,
                                    @Part MultipartBody.Part file);

    //查询自己信息
    @GET("user/my_outline")
    Call<ComplexResult> getMyMsg(@Header("Authorization") String token);

    //查询他人信息
    @GET("user/{id}/user_outline")
    Call<ComplexResult> getUserMsg(@Path("id") String userId, @Header("Authorization") String token);

    //查询他人帖子信息id数组
    @GET("user/{id}/user_post")
    Call<SimpleResult> userPosts(@Path("id") String userId, @Header("Authorization") String token);

    //搜索用户
    @GET("user/search")
    Call<SimpleResult> searchUser(@Header("Authorization") String token, @Query("query_string") String query_string);


}
