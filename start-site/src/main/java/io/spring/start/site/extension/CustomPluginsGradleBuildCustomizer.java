package io.spring.start.site.extension;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class CustomPluginsGradleBuildCustomizer implements BuildCustomizer<GradleBuild> {

  @Override
  public void customize(GradleBuild build) {
    build.addPlugin("idea");
    build.addPlugin("org.sonarqube", "2.7");
    build.addPlugin("com.gorylenko.gradle-git-properties", "2.0.0");
  }
}
