package cn.mycommons.codeanalysis

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

public class AnalysePlugin implements Plugin<Project> {

    public static final APPLICATION = 'com.android.application'
    public static final LIBRARY = 'com.android.library'


    void apply(Project project) {
        if (project.plugins.hasPlugin(APPLICATION) || project.plugins.hasPlugin(LIBRARY)) {
            project.extensions.create("analyse", AnalysePluginExtension)

            createCheckStyleTask(project)
        } else {
            logE(project, "project must apply '${APPLICATION}' or '${LIBRARY}' plugin.")
        }
    }

    private void codeAnalysis(Project project) {
//        project.android.applicationVariants.all { variant ->
//            String variantName = variant.name
//            variantName = variantName.substring(0, 1).toUpperCase() + variantName.substring(1)
//        }
    }

    static void createCheckStyleTask(Project project) {
        if (project.plugins.hasPlugin('checkstyle')) {
            logE(project, "project already use 'checkstyle' plugin.")
        } else {
            if (project.preBuild != null) {
                def analyseCheckstyleTask = project.tasks.create('analyseCheckstyleTask ', AnalyseCheckstyleTask)
                analyseCheckstyleTask.group = 'analyse'
                project.preBuild.dependsOn(analyseCheckstyleTask)
            }
        }
    }

    static void logE(Project project, String msg) {
        project.logger.log(LogLevel.ERROR, msg)
    }
}