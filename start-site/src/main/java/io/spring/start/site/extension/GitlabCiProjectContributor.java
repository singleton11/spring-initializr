package io.spring.start.site.extension;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class GitlabCiProjectContributor implements ProjectContributor {

  private final TemplateRenderer templateRenderer;

  public GitlabCiProjectContributor(TemplateRenderer templateRenderer) {
    this.templateRenderer = templateRenderer;
  }

  @Override
  public void contribute(Path projectRoot) throws IOException {
    Path file = Files.createFile(projectRoot.resolve(".gitlab-ci.yml"));
    Map<String, String> model = new HashMap<>();
    String content = templateRenderer.render("gitlabci", model);
    try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
      writer.write(content);
    }
  }
}
