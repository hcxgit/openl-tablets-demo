package com.comininglamp.openRules.beans;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 三笠阿克曼
 * @date 2021/6/16
 */
@Data
@Accessors(chain = true)
public class Message {
    public String tolist;
    public Long msgtime;
    public String msgid;
    public String action;
    public String from;
    public Text text;
    public String msgtype;
    public String roomid;
}
