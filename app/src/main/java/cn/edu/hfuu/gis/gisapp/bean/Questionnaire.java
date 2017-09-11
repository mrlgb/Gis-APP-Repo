package cn.edu.hfuu.gis.gisapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenchen on 2017/8/5.
 */

public class Questionnaire {

    /**
     * result : true
     * questionnaire_All_List : [{"overDate":"8-1-2017","questionnaireId":1,"questionnaireName":"问卷1","startDate":"7-1-2017","isEvaluate":"true"},{"overDate":"8-1-2017","questionnaireId":2,"questionnaireName":"问卷2","startDate":"7-1-2017","isEvaluate":"false"},{"overDate":"8-1-2017","questionnaireId":3,"questionnaireName":"问卷3","startDate":"7-1-2017","isEvaluate":"true"},{"overDate":"8-1-2017","questionnaireId":4,"questionnaireName":"问卷4","startDate":"7-1-2017","isEvaluate":"false"}]
     */

    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private boolean result;    private List<QuestionnaireAllListBean> questionnaire_All_List;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<QuestionnaireAllListBean> getQuestionnaire_All_List() {
        return questionnaire_All_List;
    }

    public void setQuestionnaire_All_List(List<QuestionnaireAllListBean> questionnaire_All_List) {
        this.questionnaire_All_List = questionnaire_All_List;
    }

    public static class QuestionnaireAllListBean {
        /**
         * overDate : 8-1-2017
         * questionnaireId : 1
         * questionnaireName : 问卷1
         * startDate : 7-1-2017
         * isEvaluate : true
         */

        private int scopeId=0;
        private String overDate;
        private int questionnaireId;
        private String questionnaireName;
        private String startDate;
        private String teacher;
        private String questionnaireType;
        private boolean isEvaluate;

        public boolean isEvaluate() {
            return isEvaluate;
        }

        public void setEvaluate(boolean evaluate) {
            isEvaluate = evaluate;
        }

        public String getOverDate() {
            return overDate;
        }

        public void setOverDate(String overDate) {
            this.overDate = overDate;
        }

        public int getQuestionnaireId() {
            return questionnaireId;
        }

        public int getScopeId() {
            return scopeId;
        }

        public void setScopeId(int scopeId) {
            this.scopeId = scopeId;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getQuestionnaireType() {
            return questionnaireType;
        }

        public void setQuestionnaireType(String questionnaireType) {
            this.questionnaireType = questionnaireType;
        }

        public void setQuestionnaireId(int questionnaireId) {
            this.questionnaireId = questionnaireId;
        }

        public int getScheduleId() {
            return scopeId;
        }

        public void setScheduleId(int scheduleId) {
            this.scopeId = scheduleId;
        }

        public String getQuestionnaireName() {
            return questionnaireName;
        }

        public void setQuestionnaireName(String questionnaireName) {
            this.questionnaireName = questionnaireName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }



    }
}
