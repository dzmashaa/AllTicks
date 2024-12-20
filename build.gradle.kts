// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.16" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("org.jetbrains.kotlin.jvm") version "1.9.10" apply false
}
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true) // Включити HTML-звіт
        xml.required.set(true)  // Включити XML-звіт
        txt.required.set(false) // Вимкнути текстовий звіт
    }
}