package org.example.face.os;

public enum OsType {
    WINDOWS("windows","cmd.exe"),
    LINUX("linux","/bin/sh");


    private String name;

    private String command;

    OsType(String name,String command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }


    public static OsType getCurrentOs() {
        String osName = System.getProperty("os.name").toLowerCase();
        for (OsType value : values()) {
            String name = value.getName();
            if(osName.indexOf(name) >= 0) {
                return value;
            }
        }
        return null;
    }

}
