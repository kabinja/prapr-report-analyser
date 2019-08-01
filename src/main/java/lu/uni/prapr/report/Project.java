package lu.uni.prapr.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    private final String name;
    private Map<String, Mutations> bugs;

    public Project(String name){
        this.name = name;
        this.bugs = new HashMap<>();
    }

    public void addBug(String bugId, Mutations mutations) {
        bugs.put(bugId, mutations);
    }

    public String getName() {
        return name;
    }

    public int getNumberMutantsKilled(){
        int killed = 0;

        for(Mutations mutations: bugs.values()){
            killed += mutations.getNumberMutantsKilled();
        }

        return killed;
    }

    public int getNumberMutantsSurvived(){
        int survived = 0;

        for(Mutations mutations: bugs.values()){
            survived += mutations.getNumberMutantsSurvived();
        }

        return survived;
    }

    public Map<String, Mutations> getBugs() {
        return bugs;
    }

    public List<Mutation> getMutations(Mutation.Status status) {
        List<Mutation> results = new ArrayList<>();

        for(Mutations mutations: bugs.values()){
            results.addAll(mutations.getMutations(status));
        }

        return results;
    }
}
