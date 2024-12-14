package com.jika.plugins.actuator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import freemarker.template.Configuration;
import freemarker.template.Template;


/**
 * Get all maven dependencies of a project.
 * 
 * It can be filtered by scope.
 *
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public class DependencyActuatoMojo extends AbstractMojo {
	
	/**
	 * Gives access to the Maven project information.
	 */
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;

	/**
	 * Scope to filter the dependencies.
	 */
	@Parameter(property = "scope")
	private String scope;

	/**
     * The package of generated class
     */
	@Parameter(property="apiPackage", defaultValue = "api")
	private String apiPackage;
	
	 /**
     * The output directory where the class file will be generated.
     */
	@Parameter(property="outputDirectory", defaultValue = "${project.build.directory}/generated-sources/jika")
	private File outputDirectory;
	
	private Configuration cfg;
	
	public DependencyActuatoMojo() throws IOException {
		cfg = new Configuration(Configuration.VERSION_2_3_33);
        cfg.setDefaultEncoding("UTF-8");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException {
        Path sourcePath = Paths.get(outputDirectory + "/src/main/java/" + apiPackage.replace(".", "/"));
        if (!sourcePath.toFile().exists()) {
            if (!sourcePath.toFile().mkdirs()) {
                throw new MojoExecutionException("Impossible de créer le répertoire de sortie : " + outputDirectory);
            }
        }
        
        try {
			generateDependency(sourcePath);
			generateDependencyActuator(sourcePath, project.getDependencies());
			addGeneratedSourceToClasspath(outputDirectory.getPath());
        } catch (Exception e) {
			throw new MojoExecutionException("Erreur when generating java class", e);
		}
	}

	private void generateDependencyActuator(Path targetPath, List<Dependency> dependencies) throws Exception {
		List<Dependency> actuators = dependencies.stream()
				.filter(d -> (scope == null || scope.isEmpty()) || scope.equals(d.getScope()))
				.filter(d -> (scope == null || scope.isEmpty()) ? !d.getScope().equals("test") : true)
				.sorted(Comparator.comparing(Dependency::getArtifactId))
				.collect(Collectors.toList());
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("packageName", apiPackage);
        dataModel.put("dependencies", actuators);
		generateFile("templates", "dependenciesActuator.ftl", dataModel, targetPath.toString(), "DependenciesActuator.java");
	}
	
	private void generateDependency(Path targetPath) throws Exception {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("packageName", apiPackage);
		generateFile("templates", "dependency.ftl", dataModel, targetPath.toString(), "Dependency.java");
	}
	
	private void generateFile(String templatePath, String templateName, Map<String, Object> dataModel, String targetPath, String className) throws Exception {
		InputStream templateStream = getClass().getClassLoader().getResourceAsStream(templatePath + "/" + templateName);
        if (templateStream == null) {
            throw new MojoExecutionException("Template FreeMarker introuvable dans les ressources du plugin");
        }
        
        Template template = new Template(templateName, new InputStreamReader(templateStream), cfg);
        File outputFile = new File(targetPath, className);
        FileWriter fileWriter = new FileWriter(outputFile);
        template.process(dataModel, fileWriter);
        fileWriter.close();
        templateStream.close();
	}
	
	/**
     * Adds the generated source directory to the Maven project build path.
     */
    private void addGeneratedSourceToClasspath(String outputDirectory) {
        project.addCompileSourceRoot(outputDirectory);
    }
	
}
