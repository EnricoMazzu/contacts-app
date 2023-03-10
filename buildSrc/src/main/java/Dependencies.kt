/**
 * Define versions
 */
object Versions {
    const val appAndLibPlugin = "7.3.1"
    const val gradle = "7.2.1"
    const val coreKotlin = "1.7.0"
    const val kotlinAndroid = "1.7.20"
    const val lifecycle = "2.6.0-alpha02"
    const val navigation = "2.5.0"
    const val composeUi = "1.3.2"
    const val material = "1.3.1"
    const val hilt = "2.42"
    const val activityCompose = "1.6.1"
    const val timber = "5.0.1"
    const val hiltNavigationCompose = "1.0.0"
    const val accompanistVersion = "0.28.0"
}

/**
 * Define our dependencies
 */
object Dependencies {
    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.coreKotlin}"
    const val androidxLifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val androidxLifecycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
    const val androidxActivityCompose =
        "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val androidxComposeUi = "androidx.compose.ui:ui:${Versions.composeUi}"
    const val androidxComposeUiToolingPreview =
        "androidx.compose.ui:ui-tooling-preview:${Versions.composeUi}"
    const val androidxComposeMaterial = "androidx.compose.material:material:${Versions.material}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val androidxNavigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val androidxLifecycleViewmodelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val androidxHiltNavCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    const val accompanistPermission =
        "com.google.accompanist:accompanist-permissions:${Versions.accompanistVersion}"

    const val junit = "junit:junit:4.13.2"
    const val junitExt = "androidx.test.ext:junit:1.1.3"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
    const val composeUiTestJunit = "androidx.compose.ui:ui-test-junit4:${Versions.composeUi}"
    const val androidxComposeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUi}"
    const val androidxComposeUiTestManifest =
        "androidx.compose.ui:ui-test-manifest:${Versions.composeUi}"
}

/**
 * Define classpath to add in build scripts
 */
object BuildScripts {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

/**
 * Define gradle plugin to use
 */
object PluginsDef {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val daggerHilt = "com.google.dagger.hilt.android"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kapt ="kapt"
}

