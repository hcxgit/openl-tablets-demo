package com.comininglamp.openRules.service;

import com.alibaba.fastjson.JSON;
import com.comininglamp.openRules.beans.Message;
import lombok.extern.slf4j.Slf4j;
import org.openl.rules.runtime.RulesEngineFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author 三笠阿克曼
 * @date 2021/6/15
 */
@Slf4j
public class MessageTest {

    void callRules() throws IOException {
        URL resource = MessageTest.class.getClassLoader().getResource("rules/messageTest.xlsx");
        System.out.println(resource);
        RulesEngineFactory<Service> engineFactory = new RulesEngineFactory<>(resource, Service.class);
        Service rules = engineFactory.newEngineInstance();

        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(MessageTest.class.getClassLoader().getResourceAsStream("rules/message.txt")));

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            Message message = JSON.parseObject(line, Message.class);
            boolean result = rules.ValidationMessage(message);
            if (result) {
                System.out.println("-------正确的流量-----:" + message.toString());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MessageTest mess = new MessageTest();
        mess.callRules();

    }
}
