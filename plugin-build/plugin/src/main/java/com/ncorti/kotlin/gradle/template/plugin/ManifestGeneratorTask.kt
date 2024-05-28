package com.ncorti.kotlin.gradle.template.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.redundent.kotlin.xml.xml

abstract class ManifestGeneratorTask : DefaultTask() {
    init {
        description = "Just a sample template task"

        // Don't forget to set the group here.
        // group = BasePlugin.BUILD_GROUP
    }

    @get:Input
    @get:Option(option = "message", description = "A message to be printed in the output file")
    abstract val message: Property<String>

    @get:Input
    abstract val applicationFqClassName: Property<String>

    @get:Input
    abstract val activityFqClassName: Property<String>

    // We don't need to set an explicit file, Gradle provides it for us
    @get:OutputFile
    abstract val generatedAppLinksManifestFile: RegularFileProperty

    @TaskAction
    fun sampleAction() {
        // Write the XML file that contains app links data
        val xml = xml("manifest") {
            namespace("android", "http://schemas.android.com/apk/res/android")
            "application" {
                attribute("android:name", applicationFqClassName.get())
                "activity" {
                    attribute("android:name", activityFqClassName.get())
                    "meta-data" {
                        attribute("android:name", "test")
                        attribute("android:value", message.get())
                    }
                }
            }
        }

        generatedAppLinksManifestFile.asFile.get().writeText(xml.toString())
    }
}
