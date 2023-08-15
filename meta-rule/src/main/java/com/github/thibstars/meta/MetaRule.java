package com.github.thibstars.meta;

import javax.inject.Inject;
import javax.inject.Named;
import org.apache.maven.enforcer.rule.api.AbstractEnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerLogger;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.project.MavenProject;

/**
 * @author Thibault Helsmoortel
 */
@Named("metaRule")
public class MetaRule extends AbstractEnforcerRule {

    private boolean namePresent = false;

    private boolean descriptionPresent = false;

    @Inject
    private MavenProject project;

    @Override
    public void execute() throws EnforcerRuleException {
        EnforcerLogger log = getLog();

        String name = project.getName();
        String description = project.getDescription();

        if (nothingToEnforce()) {
            log.warn("Nothing to enforce, please check the plugin configuration.");
        }

        if (this.namePresent && (name == null || name.isBlank())) {
            throw new EnforcerRuleException("No name is set.");
        }
        if (this.descriptionPresent && (description == null || description.isBlank())) {
            throw new EnforcerRuleException("No description is set.");
        }
    }

    private boolean nothingToEnforce() {
        return !this.namePresent && !this.descriptionPresent;
    }

    @Override
    public String getCacheId() {
        return Boolean.toString(this.namePresent) + this.descriptionPresent;
    }

    public void setNamePresent(boolean namePresent) {
        this.namePresent = namePresent;
    }

    public void setDescriptionPresent(boolean descriptionPresent) {
        this.descriptionPresent = descriptionPresent;
    }
}
