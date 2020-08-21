package com.sleep.plugin

import org.gradle.api.Project

class FileLog{

    static File logOutputFile;

    public static void init(Project project) {
        logOutputFile = new File(project.getBuildDir(),"TimeTracePluginLog.txt")
        if (logOutputFile.exists()) logOutputFile.deleteOnExit()
        if (!logOutputFile.getParentFile().exists())logOutputFile.getParentFile().mkdirs()
        logOutputFile.createNewFile()
    }

    public static void logI(String msg){
        logOutputFile.append(msg+"\n",false)
    }
}