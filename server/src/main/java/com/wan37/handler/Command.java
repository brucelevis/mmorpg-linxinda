package com.wan37.handler;

/**
 * 场景队列命令实体
 *
 * @author linda
 */
public class Command {

    private final GeneralHandler handler;
    private final GeneralReqMsg msg;

    public Command(GeneralHandler handler, GeneralReqMsg msg) {
        this.handler = handler;
        this.msg = msg;
    }

    public void execute() {
        handler.handle(msg);
    }
}
