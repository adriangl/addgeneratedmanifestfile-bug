package com.ncorti.kotlin.gradle.template.plugin

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.BuildConfigField
import com.android.build.api.variant.Variant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import java.io.File

const val EXTENSION_NAME = "manifestGenerator"
const val TASK_NAME = "manifestGenerator"

abstract class ManifestGeneratorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Add the 'template' extension object
        val extension = project.extensions.create(
            EXTENSION_NAME,
            ManifestGeneratorExtension::class.java,
            project
        )

        // Get the Android components extension used to iterate and do stuff with variants
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: error("Plugin has to be applied after Android Gradle Plugin")

        androidComponents.onVariants { variant ->
            val applicationName = extension.applicationFqClassName.get()
            val activityName = extension.activityFqClassName.get()
            val message = extension.message.get()

            addBuildConfigFields(variant, message)
            generateTask(project, variant, message, applicationName, activityName)
        }

        // Add a task that uses configuration from the extension object
        project.tasks.register(TASK_NAME, ManifestGeneratorTask::class.java) {
            it.message.set(extension.message)
            it.applicationFqClassName.set(extension.applicationFqClassName)
            it.activityFqClassName.set(extension.activityFqClassName)
        }
    }

    private fun addBuildConfigFields(variant: Variant, message: String) {
        with(variant) {
            buildConfigFields.put(
                "MESSAGE",
                BuildConfigField(
                    type = "String",
                    value = "\"$message\"",
                    comment = null
                )
            )
        }
    }

    private fun generateTask(
        project: Project,
        variant: Variant,
        message: String,
        applicationName: String,
        activityName: String
    ) {
        val taskName = "generate${variant.name.capitalized()}Manifest"

        val taskProvider = project.tasks.register(taskName, ManifestGeneratorTask::class.java) {
            it.message.set(message)
            it.applicationFqClassName.set(applicationName)
            it.activityFqClassName.set(activityName)
        }

        variant.sources.manifests.addGeneratedManifestFile(taskProvider, ManifestGeneratorTask::generatedAppLinksManifestFile)
    }
}
