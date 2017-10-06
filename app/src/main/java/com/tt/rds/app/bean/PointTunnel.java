package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class PointTunnel {

    @Id(autoincrement = true)
    private Long PTunnelId;
    @NotNull
    @Index(unique = true)
    private String name="";//名称
    @NotNull
    private String code="";//编码
    private String  manageOrg;//管养单位
    private double length;
    private double width;
    private double heigth;
    private int buidYear;


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
