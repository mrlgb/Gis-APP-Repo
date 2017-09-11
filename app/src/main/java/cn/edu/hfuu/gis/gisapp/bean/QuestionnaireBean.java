package cn.edu.hfuu.gis.gisapp.bean;

/**
 * Created by chenchen on 2017/8/4.
 */

public class QuestionnaireBean {
    /**
     * answerPosition : 1
     * option5 : 一星
     * option3 : 三星
     * option4 : 二星
     * option1 : 五星
     * questionName : 本次课程评价1
     * option2 : 四星
     * questionNumber : 1
     */

    private int answerPosition=-1;
    private int questionId;
    private String option5;
    private String option3;
    private String option4;
    private String option1;
    private String questionName;
    private String option2;
    private String answer;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private int questionNumber;

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        this.answerPosition = answerPosition;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public String toString() {
        return "QuestionnaireTodayListBean{" +
                "answerPosition=" + answerPosition +
                ", option5='" + option5 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", option1='" + option1 + '\'' +
                ", questionName='" + questionName + '\'' +
                ", option2='" + option2 + '\'' +
                ", questionNumber=" + questionNumber +
                '}';
    }
}
