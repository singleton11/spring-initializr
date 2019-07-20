package io.spring.start.site.extension;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.metadata.InitializrMetadata;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SwaggerProjectContributor implements ProjectContributor {

  private final TemplateRenderer templateRenderer;
  private final InitializrMetadata metadata;
  private final ResolvedProjectDescription description;

  SwaggerProjectContributor(
      TemplateRenderer templateRenderer,
      InitializrMetadata metadata,
      ResolvedProjectDescription description
  ) {
    this.templateRenderer = templateRenderer;
    this.metadata = metadata;
    this.description = description;
  }

  @Override
  public void contribute(Path projectRoot) throws IOException {
    Map<String, String> model = new HashMap<>();
    model.put("package", metadata.getPackageName().getContent());
    String renderedTemplate = templateRenderer.render("swagger-config", model);
    String packageDirectory = description
        .getBuildSystem()
        .getMainDirectory(projectRoot, description.getLanguage()) + "/" + metadata
        .getPackageName()
        .getContent()
        .replace('.', '/');
    Files.createDirectories(projectRoot.resolve(packageDirectory + "/config"));
    Path file = Files
        .createFile(projectRoot.resolve(packageDirectory + "/config/SwaggerConfig.java"));

    try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
      writer.write(renderedTemplate);
    }
  }
}
