package com.comininglamp.openRules.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comininglamp.openRules.beans.Message;
import lombok.extern.slf4j.Slf4j;
import org.openl.rules.runtime.RulesEngineFactory;

import java.io.*;
import java.net.URL;

/**
 * @author 三笠阿克曼
 * @date 2021/6/15
 */
@Slf4j
public class MessageTest {

    void callRules() {
        URL resource = MessageTest.class.getClassLoader().getResource("rules/messageTest.xlsx");
        System.out.println(resource);
        RulesEngineFactory<Service> engineFactory = new RulesEngineFactory<>(resource, Service.class);
        Service rules = engineFactory.newEngineInstance();

        String path = MessageTest.class.getClassLoader().getResource("rules/message.txt").getPath();
        JSONArray jsonList = parseJsonFile(new File(path));
        for (Object js : jsonList) {
            JSONObject jsonObject = (JSONObject) JSONArray.toJSON(js);
            Message messageData = JSON.parseObject(jsonObject.toString(), Message.class);

            boolean result = rules.ValidationMessage(messageData);
            if (result) {
                System.out.println("-------正确的流量-----:" + messageData.toString());
            }
        }
    }

    public static void main(String[] args) {
        MessageTest mess = new MessageTest();
        mess.callRules();

    }

    public static JSONArray parseJsonFile(File filepath) {
        BufferedReader reader = null;
        String tempString;
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream file = new FileInputStream(filepath);
            InputStreamReader inputFileReader = new InputStreamReader(file, "utf-8");
            reader = new BufferedReader(inputFileReader);
            while ((tempString = reader.readLine()) != null) {
                content.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("解析json文件出错:" + filepath + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return JSON.parseArray(content.toString());
    }
}
