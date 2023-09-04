package org.example.face.os.executor;


import org.example.face.os.OsType;

public class WindowsCommandExecutor implements ICommandExecutor{

    private static final OsType osType = OsType.WINDOWS;

    @Override
    public void exec(String command) {




    }

    @Override
    public OsType getOsType() {
        return osType;
    }


}
