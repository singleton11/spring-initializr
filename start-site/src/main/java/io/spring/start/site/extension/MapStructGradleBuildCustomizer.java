package io.spring.start.site.extension;

import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;

public class MapStructGradleBuildCustomizer implements BuildCustomizer<GradleBuild> {

  private final InitializrMetadata metadata;

  public MapStructGradleBuildCustomizer(InitializrMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public void customize(GradleBuild build) {
    Dependency mapstruct = metadata.getDependencies().get("mapstruct");
    build
        .dependencies()
        .add(
            "mapstruct-compile",
            io.spring.initializr.generator.buildsystem.Dependency
                .withCoordinates(mapstruct.getGroupId(), mapstruct.getArtifactId())
                .version(VersionReference.ofValue(mapstruct.getVersion()))
                .scope(DependencyScope.COMPILE_ONLY)
        );
  }
}
