package inc.app.mes.network;

import java.util.List;
import java.util.Map;
import inc.app.mes.DTO.DataDAO;
import inc.app.mes.DTO.FRequestDAO;
import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.DTO.InspectDAO;
import inc.app.mes.DTO.Message;
import inc.app.mes.DTO.PlaceDAO;
import inc.app.mes.DTO.RepairDAO;
import inc.app.mes.DTO.TokenManager;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {
    @POST("getdatas")
    Call<List<DataDAO>> getDatas(@Body Map<String, Object> param);

    @POST("login")
    Call<Message> doLogin(@Body Map<String, Object> param);

    @POST("sendmessagetome")
    Call<Void> sendMessageToMe(@Body Map<String, Object> param);

    @POST("sendmessagetofriend")
    Call<Void> sendMessageToFriend(@Body Map<String, Object> param);

    @POST("sendmessagebyvoice")
    Call<Void> sendMessageByVoice(@Body Map<String, Object> param);


    @POST("tokenavailable")
    Call<TokenManager> isTokenAvailable(@Body Map<String, Object> param);

    @POST("getnewtoken")
    Call<TokenManager> getNewToken(@Body Map<String, Object> param);

    //-----------설비 관련
    @POST("getplacelist")
    Call<List<PlaceDAO>> getPlaceList(@Body Map<String, Object> param);

    @POST("getfacilitylistperplace")
    Call<List<FacilityDAO>> getFacilityListPerPlace(@Body Map<String, Object> param);

    @POST("getfacilitydetail")
    Call<List<FacilityDAO>> getFacilityDetail(@Body Map<String, Object> param);

    //-----------설비 요청 관련
    @POST("insfrequest")
    Call<Message> insFRequest(@Body Map<String, Object> param);

    @POST("delfrequest")
    Call<Message> delFRequest(@Body Map<String, Object> param);

    @POST("getfrequestlist")
    Call<List<FRequestDAO>> getFRequestList(@Body Map<String, Object> param);

    @POST("getfrequestdetail")
    Call<List<FRequestDAO>> getFRequestDetail(@Body Map<String, Object> param);

    @POST("getfrequestdetailbyreqno")
    Call<List<FRequestDAO>> getFRequestDetailByReqNo(@Body Map<String, Object> param);

    //-----------설비 점검 관련
    @POST("insinspect")
    Call<Message> insInspect(@Body Map<String, Object> param);

    @POST("delinspect")
    Call<Message> delInspect(@Body Map<String, Object> param);

    @POST("getinspectlist")
    Call<List<InspectDAO>> getInspectList(@Body Map<String, Object> param);

    @POST("getinspectdetail")
    Call<List<InspectDAO>> getInspectDetail(@Body Map<String, Object> param);

    @POST("getinspectdetailbyinsprstno")
    Call<List<InspectDAO>> getInspectDetailByInspRstNo(@Body Map<String, Object> param);
    //-----------설비 수리 관련
    @POST("insrepair")
    Call<Message> insRepair(@Body Map<String, Object> param);

    @POST("delrepair")
    Call<Message> delRepair(@Body Map<String, Object> param);

    @POST("getrepairlist")
    Call<List<RepairDAO>> getRepairList(@Body Map<String, Object> param);

    @POST("getrepairdetail")
    Call<List<RepairDAO>> getRepairDetail(@Body Map<String, Object> param);

    @POST("getrepairdetailbyrepairno")
    Call<List<RepairDAO>> getRepairDetailByRepairNo(@Body Map<String, Object> param);
}