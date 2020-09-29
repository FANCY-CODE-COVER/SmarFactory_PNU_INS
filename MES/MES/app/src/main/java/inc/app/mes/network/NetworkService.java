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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("tokenavailable")
    Call<TokenManager> isTokenAvailable(@Query("accesstoken") String access_token);

    @POST("getnewtoken")
    Call<TokenManager> getNewToken(@Body Map<String, Object> param);

    //-----------설비 관련
    @GET("places")
    Call<List<PlaceDAO>> getPlaceList();

    @GET("facilities")
    Call<List<FacilityDAO>> getFacilityListPerPlace(@Query("placecd") String place_cd);

    @GET("facilitydetail")
    Call<List<FacilityDAO>> getFacilityDetail(@Query("facilitycd") String facility_cd);

    //-----------설비 요청 관련
    @POST("frequest")
    Call<Message> insFRequest(@Body Map<String, Object> param);

    @DELETE("frequest")
    Call<Message> delFRequest(@Query("reqno") String req_no);

    @GET("frequests")
    Call<List<FRequestDAO>> getFRequestList();

    @GET("frequestdetailf")
    Call<List<FRequestDAO>> getFRequestDetail(@Query("facilityno") String facility_no);

    @GET("frequestdetailr")
    Call<List<FRequestDAO>> getFRequestDetailByReqNo(@Query("reqno") String req_no);

    //-----------설비 점검 관련
    @POST("inspect")
    Call<Message> insInspect(@Body Map<String, Object> param);

    @DELETE("inspect")
    Call<Message> delInspect(@Query("insprstno") String insp_rst_no);

    @GET("inspects")
    Call<List<InspectDAO>> getInspectList();

    @GET("inspectdetailf")
    Call<List<InspectDAO>> getInspectDetail(@Query("facilityno") String facility_no);

    @GET("inspectdetaili")
    Call<List<InspectDAO>> getInspectDetailByInspRstNo(@Query("insprstno") String insp_rst_no);

    //-----------설비 수리 관련
    @POST("repair")
    Call<Message> insRepair(@Body Map<String, Object> param);

    @DELETE("repair")
    Call<Message> delRepair(@Query("repairno") String repair_no);

    @GET("repairs")
    Call<List<RepairDAO>> getRepairList();

    @GET("repairdetailf")
    Call<List<RepairDAO>> getRepairDetail(@Query("facilityno") String facility_no);

    @GET("repairdetailr")
    Call<List<RepairDAO>> getRepairDetailByRepairNo(@Query("repairno") String repair_no);
}