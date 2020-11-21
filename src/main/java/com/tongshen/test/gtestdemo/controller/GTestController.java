package com.tongshen.test.gtestdemo.controller;

import com.tongshen.test.gtestdemo.GeetestConfig;
import com.tongshen.test.gtestdemo.sdk.GeetestLib;
import com.tongshen.test.gtestdemo.sdk.GeetestLibResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tongshen
 * @date 2020/11/21
 */
@RestController
public class GTestController {

    @RequestMapping("/getCaptcha")
    public void doGet(HttpServletResponse response) throws ServletException, IOException {
        /*
        必传参数
            digestmod 此版本sdk可支持md5、sha256、hmac-sha256，md5之外的算法需特殊配置的账号，联系极验客服
        自定义参数,可选择添加
            user_id 客户端用户的唯一标识，确定用户的唯一性；作用于提供进阶数据分析服务，可在register和validate接口传入，不传入也不影响验证服务的使用；若担心用户信息风险，可作预处理(如哈希处理)再提供到极验
            client_type 客户端类型，web：电脑上的浏览器；h5：手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生sdk植入app应用的方式；unknown：未知
            ip_address 客户端请求sdk服务器的ip地址
        */
        GeetestLib gtLib = new GeetestLib(GeetestConfig.GEETEST_ID, GeetestConfig.GEETEST_KEY);
        String digestmod = "md5";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("digestmod", digestmod);
//        paramMap.put("user_id", userUUid);
        paramMap.put("client_type", "web");
        paramMap.put("ip_address", "127.0.0.1");
        GeetestLibResult result = gtLib.register(digestmod, paramMap);
        // 将结果状态写到session中，此处register接口存入session，后续validate接口会取出使用
        // 注意，此demo应用的session是单机模式，格外注意分布式环境下session的应用
        // 注意，不要更改返回的结构和值类型
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(result.getData());
    }
}
