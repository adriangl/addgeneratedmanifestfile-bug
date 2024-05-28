package com.ncorti.kotlin.gradle.template.plugin

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import javax.inject.Inject

const val DEFAULT_OUTPUT_FILE = "template-example.txt"

@Suppress("UnnecessaryAbstractClass")
abstract class ManifestGeneratorExtension
    @Inject
    constructor(project: Project) {
        private val objects = project.objects

        // Example of a property that is mandatory. The task will
        // fail if this property is not set as is annotated with @Optional.
        val message: Property<String> = objects.property(String::class.java)

        val applicationFqClassName: Property<String> = objects.property(String::class.java)

        val activityFqClassName: Property<String> = objects.property(String::class.java)
    }
