package cn.edu.hfuu.gis.gisapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenchen on 2017/7/21.
 */

public class DayEvaluation {

    /**
     * result : true
     * courseList : [{"courseName":"ios","section":"1,2","courseId":1},{"courseName":"435","section":"3,4","courseId":8},{"courseName":"ios","section":"7,8","courseId":1}]
     */
    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private boolean result;
    private List<CourseListBean> courseList;

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

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public static class CourseListBean {
        /**
         * courseName : ios
         * section : 1,2
         * courseId : 1
         */

        private String courseName;
        private String teacherName;
        private String classroom;
        private String section;
        private float   score;
        private int courseId;
        private int scheduleId;
        private int questionnaireId;
        private boolean isEvaluate;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public boolean isEvaluation() {
            return isEvaluate;
        }

        @Override
        public String toString() {
            return "CourseListBean{" +
                    "courseName='" + courseName + '\'' +
                    ", section='" + section + '\'' +
                    ", courseId=" + courseId +
                    ", isEvaluation=" + isEvaluate +
                    '}';
        }

        public int getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(int scheduleId) {
            this.scheduleId = scheduleId;
        }

        public int getQuestionnaireId() {
            return questionnaireId;
        }

        public void setQuestionnaireId(int questionnaireId) {
            this.questionnaireId = questionnaireId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getClassroom() {
            return classroom;
        }

        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }

        public boolean isEvaluate() {
            return isEvaluate;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public void setEvaluate(boolean evaluate) {
            isEvaluate = evaluate;
        }
    }
}
