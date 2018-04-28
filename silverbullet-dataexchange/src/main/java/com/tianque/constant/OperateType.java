package com.tianque.constant;

/**
 * Created by QQ on 2018/4/23.
 */
public enum  OperateType {
    ADD("新增","add"),DELETE("删除","delete"),UPDATE("更新","update"),DEAD("死亡","dead");
    String cname;
    String ename;
    OperateType(String cname,String ename){
        this.cname = cname;
        this.ename = ename;
    }
    public static String getName(String ename){
        for(OperateType operateType: OperateType.values()){
            if(operateType.getEname() == ename){
                return operateType.getCname();
            }
        }
        return null;
    }
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
