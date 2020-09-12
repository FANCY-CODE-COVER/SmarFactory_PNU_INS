package inc.app.mes.custum_application;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

import inc.app.mes.network.NetworkService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    private String baseUrl;
    private static MyApplication instance;
    public static MyApplication getInstance() {return instance;}
    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.instance = this;
        buildNetworkService();
        // SDK 초기화
        KakaoSDK.init(new KakaoAdapter() {
            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Context getApplicationContext() {
                        return MyApplication.this;
                    }
                };
            }
        });
    }//end onCreate()

    public NetworkService getNetworkService() {
        return networkService;
    }

    public void buildNetworkService(){
        synchronized (MyApplication.class){
            if(networkService==null){
                baseUrl="http://192.168.43.160:8082/smartfactory/";
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .create();
                GsonConverterFactory factory=GsonConverterFactory.create(gson);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(factory)
                        .build();
                networkService=retrofit.create(NetworkService.class);

            }
        }
    }

}