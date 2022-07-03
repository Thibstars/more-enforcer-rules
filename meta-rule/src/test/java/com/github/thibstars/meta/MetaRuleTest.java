package com.github.thibstars.meta;

import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Thibault Helsmoortel
 */
class MetaRuleTest {

    private MetaRule metaRule;

    private EnforcerRuleHelper helper;

    @BeforeEach
    void setUp() {
        this.metaRule = new MetaRule();

        this.helper = Mockito.mock(EnforcerRuleHelper.class);
        Mockito.when(helper.getLog()).thenReturn(Mockito.mock(Log.class));
    }

    @Test
    void testExecuteNamePresent() throws ExpressionEvaluationException {
        metaRule.setNamePresent(true);

        Mockito.when(helper.evaluate(MetaRule.PROJECT_NAME_EVAL)).thenReturn("Hello");

        Assertions.assertDoesNotThrow(() -> metaRule.execute(helper), "Execution must not throw exception.");
    }

    @Test
    void testExecuteNameNotPresent() throws ExpressionEvaluationException {
        metaRule.setNamePresent(true);

        Mockito.when(helper.evaluate(MetaRule.PROJECT_NAME_EVAL)).thenReturn(null);

        Assertions.assertThrows(EnforcerRuleException.class, () -> metaRule.execute(helper));
    }

    @Test
    void testExecuteDescriptionPresent() throws ExpressionEvaluationException {
        metaRule.setDescriptionPresent(true);

        Mockito.when(helper.evaluate(MetaRule.PROJECT_DESCRIPTION_EVAL)).thenReturn("World");

        Assertions.assertDoesNotThrow(() -> metaRule.execute(helper), "Execution must not throw exception.");
    }

    @Test
    void testExecuteDescriptionNotPresent() throws ExpressionEvaluationException {
        metaRule.setDescriptionPresent(true);

        Mockito.when(helper.evaluate(MetaRule.PROJECT_DESCRIPTION_EVAL)).thenReturn(null);

        Assertions.assertThrows(EnforcerRuleException.class, () -> metaRule.execute(helper));
    }
}