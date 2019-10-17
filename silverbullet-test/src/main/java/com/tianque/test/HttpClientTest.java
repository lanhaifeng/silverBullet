package com.tianque.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 14:31
 */
public class HttpClientTest {

    public static void main(String[] args) {
//        String s  = HttpUtil.get("http://192.168.40.208:8080/api/issueInfoXlManage/findIssueInfoForXueLiangByCondition.action?issueInfoQuery.title&issueInfoQuery.targetOrgId&issueInfoQuery.source.id&issueInfoQuery.page=1&issueInfoQuery.rows=20");
//        System.out.println(s);
        try {
            HttpClient httpClient = HttpClients.createDefault();
            //声明请求方式
            HttpGet httpGet = new HttpGet("http://192.168.40.208:8080/api/issueInfoXlManage/findIssueInfoForXueLiangByCondition.action?issueInfoQuery.title&issueInfoQuery.targetOrgId&issueInfoQuery.source.id&issueInfoQuery.page=1&issueInfoQuery.rows=20");
            //获取相应数据，这里可以获取相应的数据
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //拿到实体
            HttpEntity httpEntity= httpResponse.getEntity();
            //获取结果，这里可以正对相应的数据精细字符集的转码
            String result = "";
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity,"utf-8");
            }
            System.out.println(result);
            //关闭连接
            httpGet.releaseConnection();
        }catch (Exception e){

        }
    }
}
