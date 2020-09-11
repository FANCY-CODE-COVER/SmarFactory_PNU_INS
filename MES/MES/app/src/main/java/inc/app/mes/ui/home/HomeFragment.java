package inc.app.mes.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.DTO.PlaceDAO;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import inc.app.mes.recycler.PlaceAdapter;
import inc.app.mes.recycler.FacilityListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{
    private RecyclerView facilityListRecycler;
    private RecyclerView placeRecycler;
    private FacilityListAdapter facilityListAdapter;
    private PlaceAdapter placeAdapter;
    public ArrayList<PlaceDAO> items;
    public ArrayList<FacilityDAO> facilityDAOS;
    private NetworkService networkService;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //네트워크 서비스
        networkService = MyApplication.getInstance().getNetworkService();
        items = new ArrayList<PlaceDAO>();
        facilityDAOS =new ArrayList<FacilityDAO>();

        placeRecycler= (RecyclerView)root.findViewById(R.id.PlaceRecycler);
        placeAdapter= new PlaceAdapter(this.getActivity(), items);
        placeAdapter.setOnItemClickListener(
                new PlaceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        String place_cd =placeAdapter.getItems().get(pos).getPlace_cd();
                        getFacilityListPerPlace(place_cd);
                    }
                }
        );
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        placeRecycler.setLayoutManager(linearLayoutManager2);
        getPlaceList();
        placeRecycler.setAdapter(placeAdapter);

        facilityListRecycler = (RecyclerView)root.findViewById(R.id.HomeRecycler);
        facilityListAdapter = new FacilityListAdapter(this.getActivity(), facilityDAOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        facilityListRecycler.setLayoutManager(linearLayoutManager);
        getFacilityListPerPlace("401010");// A동 입력
        facilityListRecycler.setAdapter(facilityListAdapter);

        return root;
    }


    public void getPlaceList() {
        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("facility_cd","SS-0-01-B");
//        Log.i("SINSIN", param.get("facility_cd").toString());
        Call<List<PlaceDAO>> joinContentCall=networkService.getPlaceList(param);

        joinContentCall.enqueue(new Callback<List<PlaceDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<PlaceDAO>> call, Response<List<PlaceDAO>> response) {
                if(response.isSuccessful()){
                    List<PlaceDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    for(int i = 0; i<dataDAO.size(); ++i) {
                        placeAdapter.addItem(dataDAO.get(i));
                    }
                    placeRecycler.setAdapter(placeAdapter);
                }
                else{// 실패시 에러코드들
                    if(response.code()==500)
                    {
                        Log.i("SINSIN", "500실패");
                    }
                    else if(response.code()==503)
                    {
                        Log.i("SINSIN", "503 실패");
                    }
                    else if(response.code()==401)
                    {
                        Log.i("SINSIN", "401 실패");
                    }
                    Log.i("SINSIN", response.code()+"실패");
                    Log.i("SINSIN", response.body()+"실패");
                }
            }
            @Override
            public void onFailure(Call<List<PlaceDAO>> call, Throwable t) {
                //실패
            }
        });
    }


    public void getFacilityListPerPlace(String place_cd) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("place_cd",place_cd);
        Log.i("SINSIN", param.get("place_cd").toString());
        Call<List<FacilityDAO>> joinContentCall=networkService.getFacilityListPerPlace(param);

        joinContentCall.enqueue(new Callback<List<FacilityDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<FacilityDAO>> call, Response<List<FacilityDAO>> response) {
                if(response.isSuccessful()){
                    List<FacilityDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    facilityListAdapter.setClear();
                    for(int i = 0; i<dataDAO.size(); ++i) {
                        facilityListAdapter.addItem(dataDAO.get(i));
                    }
                    facilityListRecycler.setAdapter(facilityListAdapter);
                }
                else{// 실패시 에러코드들
                    if(response.code()==500)
                    {
                        Log.i("SINSIN", "500실패");
                    }
                    else if(response.code()==503)
                    {
                        Log.i("SINSIN", "503 실패");
                    }
                    else if(response.code()==401)
                    {
                        Log.i("SINSIN", "401 실패");
                    }
                    Log.i("SINSIN", response.code()+"실패");
                    Log.i("SINSIN", response.body()+"실패");
                }
            }
            @Override
            public void onFailure(Call<List<FacilityDAO>> call, Throwable t) {
                //실패
            }
        });
    }

}