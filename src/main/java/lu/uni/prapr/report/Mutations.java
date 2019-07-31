package lu.uni.prapr.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
import java.util.stream.Collectors;

public class Mutations {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "mutation")
    private List<Mutation> mutations;

    public List<Mutation> getMutations() {
        return mutations;
    }

    public List<Mutation> getMutations(Mutation.Status mutationStatus) {
        return mutations.stream()
                .filter(m -> m.isStatus(mutationStatus))
                .collect(Collectors.toList());
    }

    public void setMutations(List<Mutation> mutations) {
        this.mutations = mutations;
    }

    public int getNumberMutantsKilled() {
        return getMutations(Mutation.Status.KILLED).size();
    }

    public int getNumberMutantsSurvived() {
        return getMutations(Mutation.Status.SURVIVED).size();
    }

    public Mutation getMutation(int i) {
        return mutations.get(0);
    }

    public float getAverageNumberTests() {
        List<Mutation> survived = getMutations(Mutation.Status.SURVIVED);
        final int sum = survived.stream().mapToInt(Mutation::getNumberCoveringTest).sum();

        if(survived.isEmpty()){
            return 0;
        }

        return (float)sum / (float)survived.size();
    }
}
