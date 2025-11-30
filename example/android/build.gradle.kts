import org.gradle.api.tasks.Delete

buildscript {
    val kotlin_version by extra("1.9.22")
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        if (project.hasProperty("android")) {
            project.extensions.getByType<com.android.build.gradle.BaseExtension>().apply {
                if (namespace == null) {
                    namespace = project.group.toString()
                }
            }
        }
    }
}

rootProject.buildDir = file("../build")
subprojects {
    project.buildDir = File("${rootProject.buildDir}/${project.name}")
}
subprojects {
    project.evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
