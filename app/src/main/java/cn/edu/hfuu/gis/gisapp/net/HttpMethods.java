package cn.edu.hfuu.gis.gisapp.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.edu.hfuu.gis.gisapp.app.Constant;
import cn.edu.hfuu.gis.gisapp.service.ApiService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpMethods {

    /**
     * 连接超时时限
     * s(秒)
     */
    private final long ConnectionTime = 10;
    /**
     * 读取超时时限
     * s(秒)
     */
    private final long ReadTime = 30;
    /**
     * 写入超时时限
     * s(秒)
     */
    private final long WriteTime = 30;

    private static HttpMethods httpMethods;
    private Retrofit retrofit;
    private ApiService apiService;

    /**
     * 构造方法
     */
    private HttpMethods() {
        Gson gson = new GsonBuilder().serializeNulls().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.HTTP)
                //配置转化库，默认是Gson
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(InputStreamConvertFactory.create())
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //设置OKHttpClient为网络客户端
                .client(genericClient())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static HttpMethods getInstance() {
        synchronized (HttpMethods.class) {
            if (httpMethods == null) {
                httpMethods = new HttpMethods();
            }
        }
        return httpMethods;
    }

    private OkHttpClient genericClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(ConnectionTime, TimeUnit.SECONDS)
                .readTimeout(ReadTime, TimeUnit.SECONDS)
                .writeTimeout(WriteTime, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=ISO-8859-1")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .build();
                        Response response = chain.proceed(request);
                        return response;
                    }
                }).build();
        return client;
    }

    private void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 登陆
     *
     * @param subscriber
     * @param username   用户名
     * @param password   密码
     */
//    public void login(Subscriber<User> subscriber, String username, String password) {
//        Observable observable = apiService.login(username, password).map(new HttpResultFunLogin());
//        toSubscribe(observable, subscriber);
//    }

//    public void UpdatePassword(Subscriber<UpdatPassword> subscriber, String username, String password) {
//        Observable observable = apiService.UpdatePassword(username, password).map(new HttpResultFunUpdataPassword());
//        toSubscribe(observable, subscriber);
//    }
//
//    public void GetCouse(Subscriber<List<DayEvaluation.CourseListBean>> subscriber, String username, String date) {
//
//        Observable observable = apiService.GetCouse(username, date).map(new HttpResultFunGetCouse());
//        toSubscribe(observable, subscriber);
//    }
//
//    public void GetTodayQuestionnaire(Subscriber<ParticularsQuestionnaire> subscriber, String username, int scheduleId, int questionnaireId) {
//
//        Observable observable = apiService.GetTodayQuestionnaire(username, scheduleId,questionnaireId).map(new HttpResultFunGetTodayQuestionnaire());
//        toSubscribe(observable, subscriber);
//
//    }
//
//    public void GetQuestionnaireList(Subscriber<Questionnaire> subscriber, String username) {
//
//        Observable observable = apiService.GetQuestionnaireList(username).map(new HttpResultFunGetQuestionnaireList());
//        toSubscribe(observable, subscriber);
//
//    }
//
//    public void GetQuestionnaireDetail(Subscriber<ParticularsQuestionnaire> subscriber, String username, int scopeId,int questionnaireId,boolean isEvaluate) {
//
//        Observable observable = apiService.GetQuestionnaireDetail(username, scopeId,questionnaireId,isEvaluate).map(new HttpResultFunGetTodayQuestionnaire());
//        toSubscribe(observable, subscriber);
//
//    }
//    public void SubmitTodayQuestionnaire(Subscriber<SubmitQusetionnaire> subscriber, String json) {
//        Observable observable = apiService.SubmitTodayQuestionnaire(json).map(new HttpResultFunSubmitQuestionnaire());
//        toSubscribe(observable, subscriber);
//    }
//    public void SubmitQuestionnaireDetail(Subscriber<SubmitQusetionnaire> subscriber, String json) {
//        Observable observable = apiService.SubmitQuestionnaireDetail(json).map(new HttpResultFunSubmitQuestionnaire());
//        toSubscribe(observable, subscriber);
//    }
//    public void SetMsgRead(Subscriber<SubmitQusetionnaire> subscriber, int id) {
//        Observable observable = apiService.SetMsgRead(id).map(new HttpResultFunSubmitQuestionnaire());
//        toSubscribe(observable, subscriber);
//    }
//    private class HttpResultFunSubmitQuestionnaire implements Func1<SubmitQusetionnaire, SubmitQusetionnaire> {
//
//        @Override
//        public SubmitQusetionnaire call(SubmitQusetionnaire submit) {
//            Log.e("ddddd", submit.getCode() + "" + submit.isResult());
//            if (submit.getCode() != 0 || !submit.isResult()) {
//                throw new ApiException(submit.getCode());
//            }
//            return submit;
//        }
//    }
//
//    private class HttpResultFunGetTodayQuestionnaire implements Func1<ParticularsQuestionnaire, ParticularsQuestionnaire> {
//
//        @Override
//        public ParticularsQuestionnaire call(ParticularsQuestionnaire questionnaire) {
//            if (questionnaire.getCode() != 0 || !questionnaire.isResult()) {
//                throw new ApiException(questionnaire.getCode());
//            }
//            return questionnaire;
//        }
//    }
//
//    private class HttpResultFunGetQuestionnaireList implements Func1<Questionnaire, List<Questionnaire.QuestionnaireAllListBean>> {
//
//        @Override
//        public List<Questionnaire.QuestionnaireAllListBean> call(Questionnaire questionnaire) {
//            if (questionnaire.getCode() != 0 || !questionnaire.isResult()) {
//                throw new ApiException(questionnaire.getCode());
//            }
//            return questionnaire.getQuestionnaire_All_List();
//        }
//    }
//
//    /**
//     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
//     *
//     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
//     */
//    private class HttpResultFunGetCouse implements Func1<DayEvaluation, List<DayEvaluation.CourseListBean>> {
//
//        @Override
//        public List<DayEvaluation.CourseListBean> call(DayEvaluation couse) {
//            if (couse.getCode() != 0 || !couse.isResult()) {
//                throw new ApiException(couse.getCode());
//            }
//            return couse.getCourseList();
//        }
//    }
//
//    private class HttpResultFunLogin implements Func1<User, User> {
//
//        @Override
//        public User call(User user) {
//            if (user.getCode() != 0 || !user.isResult()) {
//                throw new ApiException(user.getCode());
//
//            }
//            return user;
//        }
//    }
//
//    private class HttpResultFunUpdataPassword implements Func1<UpdatPassword, UpdatPassword> {
//
//        @Override
//        public UpdatPassword call(UpdatPassword password) {
//            if (password.getCode() != 0 || !password.isResult()) {
//                throw new ApiException(password.getCode());
//            }
//            return password;
//        }
//
//    }
}