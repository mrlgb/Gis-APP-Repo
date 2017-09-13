package com.tt.rds.app.service;

import com.tt.rds.app.app.Constant;
import com.tt.rds.app.bean.User;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {


    @POST(Constant.LOGIN)
    Observable<User> login(@Query("userName") String userName, @Query("password") String password);
//    @POST(Constant.UPDATPASSWORD)
//    Observable<UpdatPassword> UpdatePassword(@Query("userName") String userName, @Query("newPassword") String newPassword);
//    @POST(Constant.GETCOURSE)
//    Observable<DayEvaluation> GetCouse(@Query("userName") String userName, @Query("date") String date);
//    @POST(Constant.GETTODAY)
//    Observable<ParticularsQuestionnaire> GetTodayQuestionnaire(@Query("userName") String userName, @Query("scheduleId") int scheduleId, @Query("questionnaireId") int questionnaireId);
//    @POST(Constant.SUBMITTODAY)
//    Observable<SubmitQusetionnaire> SubmitTodayQuestionnaire(@Query("json") String json);
//    @POST(Constant.GETQUESTIONNAIREALL)
//    Observable<Questionnaire>GetQuestionnaireList(@Query("userName") String userName);
//    @POST(Constant.GETQUESTIONNAIREDETAIL)
//    Observable<ParticularsQuestionnaire> GetQuestionnaireDetail(@Query("userName") String userName, @Query("scopeId") int scheduleId, @Query("questionnaireId") int questionnaireId, @Query("isEvaluate") boolean isEvaluate);
//    @POST(Constant.SUBMITDetail)
//    Observable<SubmitQusetionnaire> SubmitQuestionnaireDetail(@Query("json") String json);
//    @POST(Constant.MSGREAD)
//    Observable<SubmitQusetionnaire> SetMsgRead(@Query("id") int id);
}
