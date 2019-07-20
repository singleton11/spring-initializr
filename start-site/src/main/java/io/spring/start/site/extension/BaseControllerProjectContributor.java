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

public class BaseControllerProjectContributor implements ProjectContributor {

  private final TemplateRenderer templateRenderer;
  private final InitializrMetadata metadata;
  private final ResolvedProjectDescription description;

  public BaseControllerProjectContributor(
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
    FileUtils fileUtils = new FileUtils(description, metadata, projectRoot);

    // Create base controller
    fileUtils.createDirectory("/controller");
    Map<String, String> model = new HashMap<>();
    model.put("package", metadata.getPackageName().getContent());
    String baseControllerContent = templateRenderer.render("base-controller", model);
    fileUtils.createFileWithContent("/controller/BaseController.java", baseControllerContent);

    // Advice
    fileUtils.createDirectory("/controller/advice");
    String adviceContent = templateRenderer.render("advice", model);
    fileUtils.createFileWithContent("/controller/advice/ExceptionControllerAdvice.java", adviceContent);

    // Exceptions
    fileUtils.createDirectory("/exception");

    String badRequestExceptionContent = templateRenderer.render("bad-request-exception", model);
    String exceptionResponseContent = templateRenderer.render("exception-response", model);
    String forbiddenExceptionContent = templateRenderer.render("forbidden-exception", model);
    String httpExceptionContent = templateRenderer.render("http-exception", model);
    String internalServerErrorExceptionContent = templateRenderer.render("internal-server-error-exception", model);
    String notAuthorizedHttpExceptionContent = templateRenderer.render("not-authorized-http-exception", model);
    String notFoundExceptionContent = templateRenderer.render("not-found-exception", model);

    fileUtils.createFileWithContent("/exception/BadRequestException.java", badRequestExceptionContent);
    fileUtils.createFileWithContent("/exception/ExceptionResponse.java", exceptionResponseContent);
    fileUtils.createFileWithContent("/exception/ForbiddenException.java", forbiddenExceptionContent);
    fileUtils.createFileWithContent("/exception/HttpException.java", httpExceptionContent);
    fileUtils.createFileWithContent("/exception/InternalServerErrorException.java", internalServerErrorExceptionContent);
    fileUtils.createFileWithContent("/exception/NotAuthorizedHttpException.java", notAuthorizedHttpExceptionContent);
    fileUtils.createFileWithContent("/exception/NotFoundException.java", notFoundExceptionContent);
  }
}
