package android.bignerdranch.myapplication.ApiAbout;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @POST("login")
    @FormUrlEncoded
    Call<SignInResult> loginTest(@Field("username")String username, @Field("password")String password);
}
