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
public class PointStandardVillage {

    @Id(autoincrement = true)
    private Long PStandardVillageId;
    @NotNull
    @Index(unique = true)
    private String name="";//名称
    @NotNull
    private String code="";//编码

    private int population;//人口数
    private int villages;//自然村数量

    private int arriveStatus;//通达现状
    private int arriveLocation;//通达位置
    private int arriveDirection;//通达方向
    private int arriveLevel;//通达行政等级
    private String arrivePathName;//通达线路名称
    private int arrivePathCode;//通达线路编号


    private String remark="";//备注
    @NotNull
    private long userId; //用户

    private Long ttPointId;//外键关联  -点id
    @ToOne(joinProperty = "ttPointId")
    private TtPoint ttPoint;

    @NotNull
    @ToMany(referencedJoinProperty ="picId" )//指定与之关联的其他类的id
    private List<Picture> pictures;

}
