package org.task.service;

import org.task.model.VulnerabilityScript;

import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<VulnerabilityScript> vulnerabilityScripts;

    public Graph(List<VulnerabilityScript> vulnerabilityScripts) {
        this.vulnerabilityScripts = vulnerabilityScripts;
    }

    private void dfs(LinkedList<Integer> linkedList, VulnerabilityScript script) {
        if (linkedList.contains(script.getScriptId()))
            return;
        script.getDependencies()
                .forEach(scriptId -> dfs(linkedList, findById(scriptId)));
        linkedList.addLast(script.getScriptId());
    }

    public LinkedList<Integer> getScriptIds() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        vulnerabilityScripts.forEach(vulnerabilityScript -> dfs(linkedList, vulnerabilityScript));
        return linkedList;
    }

    // also I would recommend to use list of Nodes(VulnerabilityScript) (not a Nodes ids), but it also depends on
    // how many scripts you will have in DB.. if it is not many, it will be ok to use this method,
    // or create a map and group by id, so it will be possible not to use stream but get Object by key id

    private VulnerabilityScript findById(Integer id) {
        return vulnerabilityScripts
                .stream()
                .filter(v -> id == v.getScriptId())
                .findFirst()
                .orElse(null);
    }
}
