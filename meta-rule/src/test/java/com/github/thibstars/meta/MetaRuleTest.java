package com.github.thibstars.meta;

import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class MetaRuleTest {

    @InjectMocks
    private MetaRule metaRule;

    @Mock
    private MavenProject project;

    @Test
    void testExecuteNamePresent() {
        metaRule.setNamePresent(true);

        Mockito.when(project.getName()).thenReturn("Hello");

        Assertions.assertDoesNotThrow(() -> metaRule.execute(), "Execution must not throw exception.");
    }

    @Test
    void testExecuteNameNotPresent() {
        metaRule.setNamePresent(true);

        Mockito.when(project.getName()).thenReturn(null);

        Assertions.assertThrows(
                EnforcerRuleException.class,
                () -> metaRule.execute(),
                "Execution must throw exception."
        );
    }

    @Test
    void testExecuteDescriptionPresent() {
        metaRule.setDescriptionPresent(true);

        Mockito.when(project.getDescription()).thenReturn("World");

        Assertions.assertDoesNotThrow(() -> metaRule.execute(), "Execution must not throw exception.");
    }

    @Test
    void testExecuteDescriptionNotPresent() {
        metaRule.setDescriptionPresent(true);

        Mockito.when(project.getDescription()).thenReturn(null);

        Assertions.assertThrows(
                EnforcerRuleException.class,
                () -> metaRule.execute(),
                "Execution must throw exception."
        );    }
}