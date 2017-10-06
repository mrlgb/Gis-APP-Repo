package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class PointBridge {

    @Id(autoincrement = true)
    private Long PBridgeId;
    @NotNull
    @Index(unique = true)
    private String name="";//名称
    @NotNull
    private String code="";//编码

    private String status;//桥梁状况
    private int  designLoading;//设计负载
    private int materialType;//材料类型
    private int spanType;//跨径类型
    private  int dangeable;//是否危桥
    private  String managerOrg;//管养单位
    private String length;//全长
    private String width;//全宽
    private String clearWidth;//净高
    private String height;//净高
    private String spanLength;//跨径总长
    private String spanCombo;//跨径组合
    private Date buildTime;//建成时间

    private String remark="";
    @NotNull
    private long userId; //用户

    private Long ttPointId;//外键关联  -点id
    @ToOne(joinProperty = "ttPointId")
    private TtPoint ttPoint;

    @NotNull
    @ToMany(referencedJoinProperty ="picId" )//指定与之关联的其他类的id
    private List<Picture> pictures;


}
