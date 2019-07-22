package io.spring.start.site.extension;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DockerfileProjectContributor implements ProjectContributor {

  private final TemplateRenderer templateRenderer;
  private final ResolvedProjectDescription description;

  public DockerfileProjectContributor(
      ResolvedProjectDescription description,
      TemplateRenderer templateRenderer
  ) {
    this.templateRenderer = templateRenderer;
    this.description = description;
  }

  @Override
  public void contribute(Path projectRoot) throws IOException {
    Path file = Files.createFile(projectRoot.resolve("Dockerfile"));
    Map<String, String> model = new HashMap<>();
    model.put("applicationName", description.getArtifactId());
    String content = templateRenderer.render("dockerfile", model);
    try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
      writer.write(content);
    }
  }
}
