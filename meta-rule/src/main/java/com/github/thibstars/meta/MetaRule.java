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

    protected static final String PROJECT_NAME_EVAL = "${project.name}";
    protected static final String PROJECT_DESCRIPTION_EVAL = "${project.description}";

    private boolean namePresent = false;
    private boolean descriptionPresent = false;

    @Override
    public void execute(@Nonnull EnforcerRuleHelper helper) throws EnforcerRuleException {
        Log log = helper.getLog();

        try {
            String name = (String) helper.evaluate(PROJECT_NAME_EVAL);
            String description = (String) helper.evaluate(PROJECT_DESCRIPTION_EVAL);

            if (nothingToEnforce()) {
                log.warn("Nothing to enforce, please check the plugin configuration.");
            }

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

    private boolean nothingToEnforce() {
        return !this.namePresent && !this.descriptionPresent;
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

    public void setNamePresent(boolean namePresent) {
        this.namePresent = namePresent;
    }

    public void setDescriptionPresent(boolean descriptionPresent) {
        this.descriptionPresent = descriptionPresent;
    }
}
