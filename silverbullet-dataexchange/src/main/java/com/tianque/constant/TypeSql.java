package com.tianque.constant;

/**
 * Created by QQ on 2018/4/24.
 */
public enum TypeSql {
    事件("select i.id id,\n" +
            "       i.subject issue_field1,\n" +
            "       i.occurdate issue_field2,\n" +
            "       i.issuecontent issue_field3,\n" +
            "       i.occurlocation issue_field4,\n" +
            "       (select orgname from organizations where id = i.occurorg) issue_field5,\n" +
            "       (select dealtime from  (select max(dealtime) dealtime,issueid from issuelogs where dealtype = 31 group by issueid) where issueid = i. id) issue_field6,\n" +
            "       (select listagg(it.issuetypename,',') within group(order by ii.id) from issues ii left join issuehastypes iht on ii.id =iht.issueid left join issuetypes it on it.id = iht.issuetypeid where ii.id = i.id group by ii.id) issue_field7,      \n" +
            "       (select o.orgname from organizations o where i.occurorginternalcode like o.orginternalcode ||'%' and o.orglevel = 212) city\n" +
            "  from issues i where i.id = {0};","issue"),
    刑满释放人员("select b.id id,\n" +
            "       b.NAME specail_one_field1,\n" +
            "       b.IDCARDNO specail_one_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) specail_one_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) specail_one_field4,\n" +
            "       b.MOBILENUMBER specail_one_field5,\n" +
            "       b.NATIVEPLACEADDRESS specail_one_field6,\n" +
            "       b.PLACEOFORIGIN specail_one_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) specail_one_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) specail_one_field9\n" +
            "       ,B.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from POSITIVEINFOS) and b.id = {0};","specailOne"),
    社区服刑人员("select b.id id,\n" +
            "       b.NAME specail_two_field1,\n" +
            "       b.IDCARDNO specail_two_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) specail_two_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) specail_two_field4,\n" +
            "       b.MOBILENUMBER specail_two_field5,\n" +
            "       b.NATIVEPLACEADDRESS specail_two_field6,\n" +
            "       b.PLACEOFORIGIN specail_two_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) specail_two_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) specail_two_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from RECTIFICATIVEPERSONS) and b.id = {0};","specailTwo"),
    吸毒人员("select b.id id,\n" +
            "       b.NAME specail_three_field1,\n" +
            "       b.IDCARDNO specail_three_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) specail_three_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) specail_three_field4,\n" +
            "       b.MOBILENUMBER specail_three_field5,\n" +
            "       b.NATIVEPLACEADDRESS specail_three_field6,\n" +
            "       b.PLACEOFORIGIN specail_three_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) specail_three_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) specail_three_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from DRUGGYS) and b.id = {0};","specailThree"),
    易肇事肇祸严重精神障碍患者("select b.id id,\n" +
            "       b.NAME specail_four_field1,\n" +
            "       b.IDCARDNO specail_four_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) specail_four_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) specail_four_field4,\n" +
            "       b.MOBILENUMBER specail_four_field5,\n" +
            "       b.NATIVEPLACEADDRESS specail_four_field6,\n" +
            "       b.PLACEOFORIGIN specail_four_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) specail_four_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) specail_four_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from MENTALPATIENTS) and b.id = {0};","specailFour"),
    有违法犯罪行为的艾滋病病毒感染者("select b.id id,\n" +
            "       b.NAME specail_five_field1,\n" +
            "       b.IDCARDNO specail_five_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) specail_five_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) specail_five_field4,\n" +
            "       b.MOBILENUMBER specail_five_field5,\n" +
            "       b.NATIVEPLACEADDRESS specail_five_field6,\n" +
            "       b.PLACEOFORIGIN specail_five_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) specail_five_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) specail_five_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from AIDSPOPULATIONS) and b.id = {0}; ","specailFive"),
    重点上访人员("select b.id id,\n" +
            "       b.NAME specail_six_field1,\n" +
            "       b.IDCARDNO specail_six_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) specail_six_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) specail_six_field4,\n" +
            "       b.MOBILENUMBER specail_six_field5,\n" +
            "       b.NATIVEPLACEADDRESS specail_six_field6,\n" +
            "       b.PLACEOFORIGIN specail_six_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) specail_six_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) specail_six_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from SUPERIORVISITS) and b.id = {0};","specailSix"),
    重点青少年("select b.id id,\n" +
            "       b.NAME important_field1,\n" +
            "       b.IDCARDNO important_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) important_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) important_field4,\n" +
            "       b.MOBILENUMBER important_field5,\n" +
            "       b.NATIVEPLACEADDRESS important_field6,\n" +
            "       b.PLACEOFORIGIN important_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) important_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) important_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from IDLEYOUTHS) and b.id = {0};","important"),
    户籍人口("select b.id id,\n" +
            "       b.NAME houseperson_field1,\n" +
            "       b.IDCARDNO houseperson_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) houseperson_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) houseperson_field4,\n" +
            "       b.MOBILENUMBER houseperson_field5,\n" +
            "       b.NATIVEPLACEADDRESS houseperson_field6,\n" +
            "       b.PLACEOFORIGIN houseperson_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) houseperson_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) houseperson_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from householdstaffs) and b.id = {0};","houseperson"),
    流动人口("select b.id id,\n" +
            "       b.NAME nohouseperson_field1,\n" +
            "       b.IDCARDNO nohouseperson_field2,\n" +
            "       (select displayname from propertydicts where id = b.GENDER) nohouseperson_field3,\n" +
            "       (select displayname from propertydicts where id = b.NATION) nohouseperson_field4,\n" +
            "       b.MOBILENUMBER nohouseperson_field5,\n" +
            "       b.NATIVEPLACEADDRESS nohouseperson_field6,\n" +
            "       b.PLACEOFORIGIN nohouseperson_field7,\n" +
            "       (select displayname from propertydicts where id = b.faith) nohouseperson_field8,\n" +
            "       (select displayname from propertydicts where id = b.POLITICALBACKGROUND) nohouseperson_field9\n" +
            "       ,b.createdate createdate,\n" +
            "       b.city city\n" +
            "from baseinfo b where b.id in (select baseinfoid from floatingpopulations) and b.id = {0};","nohouseperson"),
    实有房屋("select h.id id,\n" +
            "       h.address               house_field1,\n" +
            "       (select orgname from organizations where id = h.orgid)    house_field2,\n" +
            "       h.HOUSEOWNER         house_field3,\n" +
            "       h.HOUSEOWNERIDCARDNO house_field4,\n" +
            "       h.MOBILENUMBER       house_field5\n" +
            "       ,h.createdate createdate,\n" +
            "       (select o.orgname from organizations o where h.orginternalcode like o.orginternalcode ||'%' and o.orglevel = 212) city\n" +
            "  from houseinfo h where h.id = {0};","house"),
    学校周边(" select s.id id,\n" +
            "       s.CHINESENAME school_field1,\n" +
            "       (select displayname from propertydicts where id = s.type) school_field2,\n" +
            "       (select displayname from propertydicts where id = s.kind) school_field3,\n" +
            "       s.GUARDCOUNT school_field4,\n" +
            "       s.GUARDHOUSECOUNT school_field5,\n" +
            "       s.TEACHERCOUNT school_field6,\n" +
            "       s.MANAGERNUM school_field7,\n" +
            "       s.ADDRESS school_field8,\n" +
            "       (select orgname from organizations where id = s.ORGID) school_field9,\n" +
            "       s.PERSONLIABLE school_field10,\n" +
            "       s.Personliablemobilenumber school_field11,\n" +
            "       s.createdate createdate,\n" +
            "       (select o.orgname from organizations o where s.orginternalcode like o.orginternalcode ||'%' and o.orglevel = 212) city\n" +
            "  from schools s where s.id = {0};","school"),
    网格员("  select g.id id ,\n" +
            "       (select orgname from organizations where id = g.orgid) gridmember_field1,\n" +
            "       g.name gridmember_field2,\n" +
            "       (select displayname from propertydicts where id = g.gender) gridmember_field3,\n" +
            "       (select displayname from propertydicts where id = g.nation) gridmember_field4,\n" +
            "       g.birthday gridmember_field5,\n" +
            "       (select displayname from propertydicts where id = g.politicalbackground) gridmember_field6,\n" +
            "       (select displayname from propertydicts where id = g.schooling) gridmember_field7,\n" +
            "       g.mobilephone gridmember_field8,\n" +
            "       g.idcardno gridmember_field9,\n" +
            "       g.position gridmember_field10 ,\n" +
            "       g.workdate gridmember_field11\n" +
            "       ,g.createdate createdate,\n" +
            "       (select o.orgname from organizations o where g.orgcode like o.orginternalcode ||'%' and o.orglevel = 212) city\n" +
            "  from gridmember g where g.id = {0};","gridmember"),
    新社会组织("select n.id id,\n" +
            "       (select orgname from organizations where id = n.ORGID) newso_field1,\n" +
            "       (select displayname from propertydicts where id = n.type) newso_field2,\n" +
            "       n.name newso_field3,\n" +
            "       n.code newso_field4,\n" +
            "       n.address newso_field5,\n" +
            "       n.legalperson newso_field6,\n" +
            "       n.legalpersontelephone newso_field7\n" +
            "       ,n.createdate createdate,\n" +
            "       (select o.orgname from organizations o where n.orginternalcode like o.orginternalcode ||'%' and o.orglevel = 212) city\n" +
            "  from newsocietyorganizations n where n.id = {0};","news"),
    非公有制经济组织("select nc.id id,\n" +
            "       nc.NAME neweo_field1,\n" +
            "       (select displayname from propertydicts where id = nc.STYLE) neweo_field2,\n" +
            "       nc.RESIDENCE neweo_field3,\n" +
            "       nc.CHIEF neweo_field4,\n" +
            "       nc.MOBILENUMBER neweo_field5,\n" +
            "       nc.CARDNO neweo_field6,\n" +
            "       nc.LICENSENUMBER neweo_field7,\n" +
            "       nc.EMPLOYEENUMBER neweo_field8,\n" +
            "       (select orgname from organizations where id = nc.ORGID) neweo_field9\n" +
            "       ,nc.createdate createdate,\n" +
            "       (select o.orgname from organizations o where nc.orginternalcode like o.orginternalcode ||'%' and o.orglevel = 212) city\n" +
            "  from neweconomicorganizations nc where nc.id = {0};","neweo");
    String sql;
    String type;
    TypeSql(String sql, String type) {
        this.sql = sql;
        this.type = type;
    }

    public static String getSql(String type){
        for(TypeSql sql: TypeSql.values()){
            if(sql.getType() == type){
                return sql.getSql();
            }
        }
        return null;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
