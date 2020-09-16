package inc.app.mes.custum_application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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
    private boolean changed;
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.instance = this;
        changed=false;
        baseUrl="http://192.168.43.160:8082/smartfactory/";
        Log.i("BASE_URL", baseUrl);
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

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = "http://"+baseUrl;
        Log.i("BASE_URL_setBaseUrl", baseUrl);
        changed=true;
        buildNetworkService();
    }

    public void buildNetworkService(){
        synchronized (MyApplication.class){
            if(networkService==null || changed){
                changed=false;
                Log.i("BASE_URL_buildNetworkService", baseUrl);
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