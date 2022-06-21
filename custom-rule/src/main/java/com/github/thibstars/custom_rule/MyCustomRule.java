package com.github.thibstars.custom_rule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

/**
 * @author Thibault Helsmoortel
 */
public class MyCustomRule implements EnforcerRule {

    private boolean fail = false;

    @Override
    public void execute(@Nonnull EnforcerRuleHelper helper) throws EnforcerRuleException {
        Log log = helper.getLog();

        try {
            // get the various expressions out of the helper.
            MavenProject project = (MavenProject) helper.evaluate("${project}");
            MavenSession session = (MavenSession) helper.evaluate("${session}");
            String target = (String) helper.evaluate("${project.build.directory}");
            String artifactId = (String) helper.evaluate("${project.artifactId}");
            String mavenVersion = (String) helper.evaluate("${maven.version}");

            // retrieve any component out of the session directly
            MavenProject resolver = helper.getComponent(MavenProject.class);

            log.info("Retrieved Target Folder: " + target);
            log.info("Retrieved ArtifactId: " + artifactId);
            log.info("Retrieved Project: " + project);
            log.info("Retrieved Maven version: " + mavenVersion);
            log.info("Retrieved Session: " + session);
            log.info("Retrieved Resolver: " + resolver);

            if (this.fail) {
                throw new EnforcerRuleException("Failing because my param said so.");
            }
        } catch (ComponentLookupException e) {
            throw new EnforcerRuleException("Unable to lookup a component " + e.getLocalizedMessage(), e);
        } catch (ExpressionEvaluationException e) {
            throw new EnforcerRuleException("Unable to lookup an expression " + e.getLocalizedMessage(), e);
        }
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public boolean isResultValid(@Nonnull EnforcerRule enforcerRule) {
        return false;
    }

    @Nullable
    @Override
    public String getCacheId() {
        return Boolean.toString(this.fail);
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }
}
