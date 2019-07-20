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

public class BootstrapProjectContributor implements ProjectContributor {

  private final ResolvedProjectDescription description;
  private final TemplateRenderer templateRenderer;

  public BootstrapProjectContributor(ResolvedProjectDescription description,
      TemplateRenderer templateRenderer) {
    this.description = description;
    this.templateRenderer = templateRenderer;
  }

  @Override
  public void contribute(Path projectRoot) throws IOException {
    Files.createDirectories(projectRoot.resolve("src/main/resources"));
    Path file = Files.createFile(projectRoot.resolve("src/main/resources/bootstrap.yml"));
    Map<String, String> model = new HashMap<>();
    model.put("applicationName", description.getArtifactId());
    String content = templateRenderer.render("bootstrap", model);
    try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
      writer.write(content);
    }
  }
}
