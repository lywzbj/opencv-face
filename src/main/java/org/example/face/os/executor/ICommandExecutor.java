package org.example.face.os.executor;

import org.example.face.os.OsType;

/**
 * 命令行执行器
 */
public interface ICommandExecutor {


    void exec(String command);


    OsType getOsType();



















}
