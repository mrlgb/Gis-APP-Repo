package com.tt.rds.app.bean;

import java.util.List;


public class User {

    /**
     * result : true
     * studentNotice : [{"noticeContent":"你收到一个调查问卷-\u201c测试问卷\u201d。请速去评教！谢谢","createTime":"2017-08-25 09:07:32","isRead":false,"id":1,"noticeId":"2017082570","noticeTitle":"\u201c测试问卷\u201d问卷调查通知"},{"noticeContent":"你收到一个调查问卷-\u201c测试\u201d。请速去评教！谢谢","createTime":"2017-08-25 09:07:32","isRead":true,"id":3,"noticeId":"2017082595","noticeTitle":"\u201c测试\u201d问卷调查通知"}]
     * name : 袁杰
     * className : 17信息安全与管理（1）
     * user : 1304092008
     * NotReadNoticeNum : 1
     */

    private int code;
    private boolean result;
    private String name;
    private String className;
    private String user;
    private int NotReadNoticeNum;
    private List<StudentNoticeBean> studentNotice;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNotReadNoticeNum() {
        return NotReadNoticeNum;
    }

    public void setNotReadNoticeNum(int NotReadNoticeNum) {
        this.NotReadNoticeNum = NotReadNoticeNum;
    }

    public List<StudentNoticeBean> getStudentNotice() {
        return studentNotice;
    }

    public void setStudentNotice(List<StudentNoticeBean> studentNotice) {
        this.studentNotice = studentNotice;
    }

    public static class StudentNoticeBean {
        /**
         * noticeContent : 你收到一个调查问卷-“测试问卷”。请速去评教！谢谢
         * createTime : 2017-08-25 09:07:32
         * isRead : false
         * id : 1
         * noticeId : 2017082570
         * noticeTitle : “测试问卷”问卷调查通知
         */

        private String noticeContent;
        private String createTime;
        private boolean isRead;
        private int id;
        private String noticeId;
        private String noticeTitle;

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(String noticeId) {
            this.noticeId = noticeId;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }
    }
}
