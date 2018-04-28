package com.tianque.constant;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 */
public enum TableName{
    事件("issue_for_exchange","issue"),刑满释放人员("specail_one_for_exchange","specailOne"),
    社区服刑人员("specail_two_for_exchange","specailTwo"),吸毒人员("specail_three_for_exchange","specailThree"),
    易肇事肇祸严重精神障碍患者("specail_four_for_exchange","specailFour"),有违法犯罪行为的艾滋病病毒感染者("specail_five_for_exchange ","specailFive"),
    重点上访人员("specail_six_for_exchange","specailSix"),重点青少年("important_for_exchange","important"),
    户籍人口("houseperson_for_exchange","houseperson"),流动人口("nohouseperson_for_exchange","nohouseperson"),
    实有房屋("house_for_exchange","house"),学校周边("school_for_exchange","school"),
    网格员("gridmember_for_exchange","gridmember"),新社会组织("news_for_exchange","news"),
    非公有制经济组织("neweo_for_exchange","neweo");
    String tableName;
    String type;
    TableName(String tablename, String type) {
        this.tableName = tablename;
        this.type = type;
    }

    public static String getTableName(String type){
        for(TableName tableName: TableName.values()){
            if(tableName.getType() == type){
                return tableName.getTableName();
            }
        }
        return null;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
