package ${packageName};

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "dependencies")
public class DependenciesActuator {

	@ReadOperation
	public List<Dependency> events() {
		List<Dependency> dependencies = new ArrayList<>();
		<#list dependencies as dep>
        dependencies.add(new Dependency("${dep.getArtifactId()}", "${dep.getVersion()}"));
        </#list>
		return dependencies;
	}

}
