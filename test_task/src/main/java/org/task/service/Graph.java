package org.task.service;

import lombok.extern.slf4j.Slf4j;
import org.task.exceptions.CycledDependencyException;
import org.task.exceptions.NoLinkedScriptsException;
import org.task.model.VulnerabilityScript;

import java.util.LinkedList;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
public class Graph {
    private List<VulnerabilityScript> vulnerabilityScripts;

    public Graph(List<VulnerabilityScript> vulnerabilityScripts) {
        this.vulnerabilityScripts = vulnerabilityScripts;
    }

    void dfs(List<Integer> parents, LinkedList<Integer> linkedList, VulnerabilityScript script) throws CycledDependencyException, NoLinkedScriptsException {

        if (parents.contains(script.getScriptId()))
            throw new CycledDependencyException("There is cycled dependency in script");

        if (linkedList.contains(script.getScriptId()))
            return;

        parents.add(script.getScriptId());

        for (Integer scriptId : emptyIfNull(script.getDependencies())) {
            dfs(parents, linkedList, findById(scriptId));
        }

        parents.remove((Object) script.getScriptId());
        linkedList.addLast(script.getScriptId());
    }

    public LinkedList<Integer> getScriptIds() {
        LinkedList<Integer> scriptsId = new LinkedList<>();
        List<Integer> parentList = new LinkedList<>();

        vulnerabilityScripts.forEach(vulnerabilityScript -> {
            try {
                dfs(parentList, scriptsId, vulnerabilityScript);
            } catch (CycledDependencyException | NoLinkedScriptsException e) {
                log.error("Error was occurred with Exception : {} , and message : {}", e.getClass(), e.getMessage());
            }
        });

        return scriptsId;
    }

    // also I would recommend to use list of Nodes(VulnerabilityScript) (not a Nodes ids), but it also depends on
    // how many scripts you will have in DB.. if it is not many, it will be ok to use this method,
    // or create a map and group by id, so it will be possible not to use stream but get Object by key id

    private VulnerabilityScript findById(Integer id) throws NoLinkedScriptsException {
        return vulnerabilityScripts
                .stream()
                .filter(v -> id == v.getScriptId())
                .findFirst()
                .orElseThrow(() -> new NoLinkedScriptsException("No Nodes was found with such ids"));
    }
}
