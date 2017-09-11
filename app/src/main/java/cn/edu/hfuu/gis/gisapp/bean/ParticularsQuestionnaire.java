package cn.edu.hfuu.gis.gisapp.bean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenchen on 2017/8/4.
 */

public class ParticularsQuestionnaire {

    /**
     * result : true
     * teacher : 李老师
     * questionnaireId : 1
     * className : 软件工程
     * classroom : 36栋202
     * section : 1,2节
     * department : 计算机系
     * questionnaire_today_List : [{"answerPosition":1,"option5":"一星","option3":"三星","option4":"二星","option1":"五星","questionName":"本次课程评价1","option2":"四星","questionNumber":1},{"answerPosition":1,"option5":"一星","option3":"三星","option4":"二星","option1":"五星","questionName":"本次课程评价2","option2":"四星","questionNumber":1}]
     */
    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private boolean result;
    private String teacher;
    private int questionnaireId;
    private String className;
    private String courseName;
    private String classroom;
    private String section;
    private String department;
    private boolean isEvaluate;
    private boolean ifEva;
    private List<QuestionnaireBean> questionnaire_today_List;
    private List<QuestionnaireBean> questionnaire_Detail_List;
    public boolean isEvaluate() {
        return isEvaluate;
    }

    public void setEvaluate(boolean evaluate) {
        isEvaluate = evaluate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isIfEva() {
        return ifEva;
    }

    public void setIfEva(boolean ifEva) {
        this.ifEva = ifEva;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<QuestionnaireBean> getQuestionnaire_today_List() {
        return questionnaire_today_List;
    }

    public void setQuestionnaire_today_List(List<QuestionnaireBean> questionnaire_today_List) {
        this.questionnaire_today_List = questionnaire_today_List;
    }

    public List<QuestionnaireBean> getQuestionnaire_Detail_List() {
        return questionnaire_Detail_List;
    }

    public void setQuestionnaire_Detail_List(List<QuestionnaireBean> questionnaire_Detail_List) {
        this.questionnaire_Detail_List = questionnaire_Detail_List;
    }
}
