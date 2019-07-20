package io.spring.start.site.util;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

  private final String packageDirectory;
  private final Path projectRoot;

  public FileUtils(
      ResolvedProjectDescription description,
      InitializrMetadata metadata,
      Path projectRoot
  ) {
    this.projectRoot = projectRoot;

    packageDirectory = description
        .getBuildSystem()
        .getMainDirectory(projectRoot, description.getLanguage()) + "/" + metadata
        .getPackageName()
        .getContent()
        .replace('.', '/');
  }


  public void createFileWithContent(String path, String content) throws IOException {
    Path file = Files.createFile(projectRoot.resolve(packageDirectory + path));
    try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
      writer.write(content);
    }
  }

  public void createDirectory(String path) throws IOException {
    Files.createDirectories(projectRoot.resolve(packageDirectory + path));
  }
}
