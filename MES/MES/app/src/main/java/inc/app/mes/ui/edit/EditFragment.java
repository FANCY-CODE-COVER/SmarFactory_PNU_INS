package inc.app.mes.ui.edit;

import android.annotation.SuppressLint;
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

import inc.app.mes.DTO.FRequestDAO;
import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.DTO.InspectDAO;
import inc.app.mes.DTO.RepairDAO;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import inc.app.mes.recycler.MenuAdapter;
import inc.app.mes.recycler.InspectAdapter;
import inc.app.mes.recycler.RepairAdapter;
import inc.app.mes.recycler.RequestAdapter;
import inc.app.mes.util.respnoseLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFragment extends Fragment {

    private RecyclerView menuRecycler;
    private RecyclerView editRecycler;

    private MenuAdapter menuAdapter;
    private RequestAdapter requestAdapter;
    private InspectAdapter inspectAdapter;
    private RepairAdapter repairAdapter;
    public ArrayList<String> title = new ArrayList<String>();
    public ArrayList<String> state = new ArrayList<String>();
    private NetworkService networkService;
    private String menu;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        networkService = MyApplication.getInstance().getNetworkService();
        menuRecycler = (RecyclerView)root.findViewById(R.id.MenuRecycler);
        menuAdapter =new MenuAdapter(this.getContext());
        menuAdapter.setOnItemClickListener(
                new MenuAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        menu = menuAdapter.getItems().get(pos);
                        if(menu.equals("요청")){
                            getFRequestList();
                        }
                        else if (menu.equals("점검")){
                            getInspectList();
                        }
                        else if(menu.equals("수리")){
                            getRepairList();
                        }

                    }
                }
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        menuRecycler.setLayoutManager(linearLayoutManager);
        menuAdapter.addItem("요청");
        menuAdapter.addItem("점검");
        menuAdapter.addItem("수리");
        menuRecycler.setAdapter(menuAdapter);
        editRecycler = (RecyclerView)root.findViewById(R.id.EditRecycler);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        editRecycler.setLayoutManager(linearLayoutManager2);
        requestAdapter=new RequestAdapter(this.getContext());
        inspectAdapter=new InspectAdapter(this.getContext());
        repairAdapter=new RepairAdapter(this.getContext());
        getFRequestList();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(menu==null){
            menu="요청";
        }

        if(menu.equals("요청")){
            getFRequestList();
        }
        else if (menu.equals("점검")){
            getInspectList();
        }
        else if(menu.equals("수리")){
            getRepairList();
        }

    }

    public void getFRequestList() {

        Call<List<FRequestDAO>> joinContentCall=networkService.getFRequestList();

        joinContentCall.enqueue(new Callback<List<FRequestDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<FRequestDAO>> call, Response<List<FRequestDAO>> response) {
                if(response.isSuccessful()){
                    List<FRequestDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    requestAdapter.setClear();
                    for(int i = 0; i<dataDAO.size(); ++i) {
                        requestAdapter.addItem(dataDAO.get(i));
                    }
                    editRecycler.setAdapter(requestAdapter);
                }
                else{// 실패시 에러코드들
                    try {
                        respnoseLogger.doPrint(response.code());
                    }
                    catch (Exception e){
                        Log.e("ERROR",e.toString());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<FRequestDAO>> call, Throwable t) {
                //실패
            }
        });
    }


    public void getInspectList() {
        Call<List<InspectDAO>> joinContentCall=networkService.getInspectList();

        joinContentCall.enqueue(new Callback<List<InspectDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<InspectDAO>> call, Response<List<InspectDAO>> response) {
                if(response.isSuccessful()){
                    List<InspectDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    inspectAdapter.setClear();
                    for(int i = 0; i<dataDAO.size(); ++i) {
                        inspectAdapter.addItem(dataDAO.get(i));
                    }
                    editRecycler.setAdapter(inspectAdapter);
                }
                else{// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
                }
            }
            @Override
            public void onFailure(Call<List<InspectDAO>> call, Throwable t) {
                //실패
            }
        });
    }
    public void getRepairList() {
        Call<List<RepairDAO>> joinContentCall=networkService.getRepairList();

        joinContentCall.enqueue(new Callback<List<RepairDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<RepairDAO>> call, Response<List<RepairDAO>> response) {
                if(response.isSuccessful()){
                    List<RepairDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    repairAdapter.setClear();
                    for(int i = 0; i<dataDAO.size(); ++i) {
                        repairAdapter.addItem(dataDAO.get(i));
                    }
                    editRecycler.setAdapter(repairAdapter);
                }
                else{// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
                }
            }
            @Override
            public void onFailure(Call<List<RepairDAO>> call, Throwable t) {
                //실패
            }
        });
    }


}