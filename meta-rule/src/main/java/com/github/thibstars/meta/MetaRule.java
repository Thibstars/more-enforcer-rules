package com.github.thibstars.meta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

/**
 * @author Thibault Helsmoortel
 */
public class MetaRule implements EnforcerRule {

    private boolean namePresent = false;
    private boolean descriptionPresent = false;

    @Override
    public void execute(@Nonnull EnforcerRuleHelper helper) throws EnforcerRuleException {
        Log log = helper.getLog();

        try {
            String name = (String) helper.evaluate("${project.name}");
            String description = (String) helper.evaluate("${project.description}");

            log.info("Retrieved Name: " + name);
            log.info("Retrieved Description: " + description);

            if (this.namePresent && (name == null || name.isBlank())) {
                throw new EnforcerRuleException("No name is set.");
            }
            if (this.descriptionPresent && (description == null || description.isBlank())) {
                throw new EnforcerRuleException("No Description is set.");
            }
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
        return Boolean.toString(this.namePresent) + this.descriptionPresent;
    }

    @SuppressWarnings("unused") // Used by maven
    public void setNamePresent(boolean namePresent) {
        this.namePresent = namePresent;
    }

    @SuppressWarnings("unused") // Used by maven
    public void setDescriptionPresent(boolean descriptionPresent) {
        this.descriptionPresent = descriptionPresent;
    }
}
