package com.sleep.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.sleep.plugin.internal.TimeTrace
import org.gradle.api.Plugin
import org.gradle.api.Project

class AopPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.dependencies {
            implementation "org.aspectj:aspectjrt:1.9.6"
        }
        FileLog.init(project)
        project.extensions.create("aspectjx", AJXExtension)
        if (project.plugins.hasPlugin(AppPlugin)) {
            //build time trace
            project.gradle.addListener(new TimeTrace())

            //register AspectTransform
            AppExtension android = project.extensions.getByType(AppExtension)
            android.registerTransform(new AJXTransform(project))
        }
    }
}