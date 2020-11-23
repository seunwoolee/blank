//package com.shuneesoft.blanker.http;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitClient {
////    private static final String BASE_URL = "https://jangboso.co.kr/";
//    private static final String BASE_URL = "http://192.168.0.58:8000/";
//
//
//    public static HttpService getHttpService (String key) {
//        return getInstance(key).create(HttpService.class);
//    }
//
//    private static Retrofit getInstance(String key) {
//        Gson gson = new GsonBuilder()
//        .setLenient()
//        .create();
//
//         Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson));
//
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        Interceptor interceptor = new Interceptor(){
//            @NotNull
//            @Override
//            public Response intercept(@NotNull Chain chain) throws IOException {
//                Request newRequest = null;
//                if(key == null) {
//                    newRequest  = chain.request().newBuilder()
//                            .build();
//                } else {
//                    newRequest  = chain.request().newBuilder()
//                            .addHeader("Authorization", "Token " + key)
//                            .build();
//                }
//                return chain.proceed(newRequest);
//            }
//        };
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(logInterceptor).addInterceptor(interceptor);
//
//        OkHttpClient client = builder.build();
//        retrofitBuilder.client(client);
//
//        return retrofitBuilder.build();
//    }
//
//}
