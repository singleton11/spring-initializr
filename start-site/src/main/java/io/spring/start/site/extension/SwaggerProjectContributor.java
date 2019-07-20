package io.spring.start.site.extension;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.start.site.util.FileUtils;
import java.io.IOException;
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

    FileUtils fileUtils = new FileUtils(description, metadata, projectRoot);

    fileUtils.createDirectory("/config");
    fileUtils.createFileWithContent("/config/SwaggerConfig.java", renderedTemplate);
  }
}
