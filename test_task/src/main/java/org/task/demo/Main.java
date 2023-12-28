package org.task.demo;

import org.task.model.VulnerabilityScript;
import org.task.service.Graph;

import java.util.List;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        VulnerabilityScript script1 = new VulnerabilityScript(1, List.of(2, 3));
        VulnerabilityScript script2 = new VulnerabilityScript(2, List.of(4, 5));
        VulnerabilityScript script3 = new VulnerabilityScript(3, List.of(4, 6));
        VulnerabilityScript script4 = new VulnerabilityScript(4, List.of());
        VulnerabilityScript script5 = new VulnerabilityScript(5, List.of());
        VulnerabilityScript script6 = new VulnerabilityScript(6, List.of());


        List<VulnerabilityScript> vulnerabilityScripts = List.of(script1, script2,
                script3, script4, script5, script6);

        Graph graph = new Graph(vulnerabilityScripts);
        // you can use ids to get scripts from bd, or to print it, or etc
        graph.getScriptIds().forEach(out::println);
    }
}
