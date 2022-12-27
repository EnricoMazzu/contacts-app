/*buildscript {
    ext {
        compose_ui_version = '1.3.2'
        activity_compose_version = '1.6.1'
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '7.3.1' apply false
    id 'com.android.library' version '7.3.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.7.20' apply false
}*/

buildscript {

    dependencies {
        classpath(BuildScripts.gradle)
        classpath(BuildScripts.hilt)
    }

}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id (PluginsDef.androidApplication) version Versions.appAndLibPlugin apply  false
    id (PluginsDef.androidLibrary) version  Versions.appAndLibPlugin apply false
    id (PluginsDef.kotlinAndroid) version Versions.kotlinAndroid apply false
    id (PluginsDef.daggerHilt) version Versions.hilt apply false
}