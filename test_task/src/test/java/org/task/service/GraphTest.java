package org.task.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.task.exceptions.CycledDependencyException;
import org.task.model.VulnerabilityScript;

import java.util.LinkedList;
import java.util.List;

public class GraphTest {

    @Test
    public void getScriptIdsTest() {
        VulnerabilityScript script1 = new VulnerabilityScript(1, List.of(2, 3));
        VulnerabilityScript script2 = new VulnerabilityScript(2, List.of(4, 5));
        VulnerabilityScript script3 = new VulnerabilityScript(3, List.of(4, 6));
        VulnerabilityScript script4 = new VulnerabilityScript(4, List.of());
        VulnerabilityScript script5 = new VulnerabilityScript(5, List.of());
        VulnerabilityScript script6 = new VulnerabilityScript(6, List.of());

        List<VulnerabilityScript> vulnerabilityScripts = List.of(script1, script2,
                script3, script4, script5, script6);

        Graph graph = new Graph(vulnerabilityScripts);
        List<Integer> actual = graph.getScriptIds();

        Assertions.assertEquals(actual, getExcpectedList(), "List of scripts are not in sane order");
    }

    @Test
    public void getScriptIdsTestNullDependencies() {
        VulnerabilityScript script1 = new VulnerabilityScript(1, List.of(2, 3));
        VulnerabilityScript script2 = new VulnerabilityScript(2, List.of(4, 5));
        VulnerabilityScript script3 = new VulnerabilityScript(3, List.of(4, 6));
        VulnerabilityScript script4 = new VulnerabilityScript(4, null);
        VulnerabilityScript script5 = new VulnerabilityScript(5, null);
        VulnerabilityScript script6 = new VulnerabilityScript(6, null);


        List<VulnerabilityScript> vulnerabilityScripts = List.of(script1, script2,
                script3, script4, script5, script6);

        Graph graph = new Graph(vulnerabilityScripts);
        List<Integer> actual = graph.getScriptIds();

        Assertions.assertEquals(actual, getExcpectedList(), "List of scripts are not in sane order");
    }

    @Test
    public void getScriptIdsCycledDependencyExceptionTest() {
        VulnerabilityScript script3 = new VulnerabilityScript(3, List.of(4, 6, 1));
        List<Integer> parentList = List.of(3);

        Graph graph = new Graph(List.of(script3));

        CycledDependencyException expected = Assertions.assertThrows(CycledDependencyException.class, () ->
                graph.dfs(parentList, new LinkedList<>(), script3));

        Assertions.assertEquals(expected.getMessage(), "There is cycled dependency in script");
    }

    private List<Integer> getExcpectedList() {
        return List.of(4, 5, 2, 6, 3, 1);
    }
}
