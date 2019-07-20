package io.spring.start.site.extension;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class CustomTasksGradleBuildCustomizer implements BuildCustomizer<GradleBuild> {

  @Override
  public void customize(GradleBuild build) {
    build.customizeTask("test", test -> {
      test.nested(
          "testLogging",
          taskCustomization -> taskCustomization
              .invoke(
                  "events",
                  "\"PASSED\", \"STARTED\", \"FAILED\", \"SKIPPED\""
              )
      );
      test.set("failfast", "true");
    });

    build.customizeTask("jar", jar -> {
      jar.set("archivesBaseName", "\"${project.name}\"");
      jar.set("project.version", "\"\"");
    });
  }
}
