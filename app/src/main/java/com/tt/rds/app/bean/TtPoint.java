package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by guobinli on 2017/10/5.
 */

@Entity
public class TtPoint {
    @Id(autoincrement = true)
    private Long ttPointId;

    @NotNull
    @Index(unique = true)
    private String name;//* 名称
    private String code;//* 编号


    private Long pTypeId;//关联 -点类型
    @ToOne(joinProperty = "pTypeId")
    private PointType pointType;

    private String pathName; //路线名称
    private Long   pathCode;  //路线代码
    private String  sectionNo;//路段序列号
    @NotNull
    private String adminCode;  //行政区划
    @NotNull
    private  double lat;  //经度
    @NotNull
    private  double lon; //纬度
    @NotNull
    private  double alt; //高度

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


}
