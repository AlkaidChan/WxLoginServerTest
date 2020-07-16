package red.orenji.testlogin.controller;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WxLoginController {
    @GetMapping("/wxLogin")
    public void login(@RequestParam String code) throws URISyntaxException {
        System.out.println("被访问");
        System.out.println(code);


        String url = "https://api.weixin.qq.com/sns/jscode2session?";

        Map<String, String> params = new HashMap<>();
        params.put("appid", "wx7610a77bc05c8418");
        params.put("secret", "02d17fd3d55f1fdad45c599bbfdd0385");
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        URIBuilder builder = new URIBuilder(url);
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addParameter(key, params.get(key));
            }
        }

        URI uri = builder.build();
        System.out.println(uri.toString());
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建get请求
        HttpGet httpGet = new HttpGet(uri);

        // 创建响应模型
        CloseableHttpResponse response = null;

        try {
            System.out.println(httpClient);
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(response.getStatusLine());
        System.out.println(response.getLocale());
        try {
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
