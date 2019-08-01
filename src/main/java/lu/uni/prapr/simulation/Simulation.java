package lu.uni.prapr.simulation;

import lu.uni.prapr.report.Mutation;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public static double calculateExpectation(List<Mutation> mutations, double flakiness) {
        if(mutations.isEmpty()){
            return 0;
        }

        final List<Double> probabilities = calculatePatchProbabilities(mutations, flakiness);
        return probabilities.stream().map(n -> 1 - n).mapToDouble(Double::doubleValue).sum();
    }

    public static double calculateProbabilityAtLeastOne(List<Mutation> mutations, double flakiness){
        if(mutations.isEmpty()){
            return 0;
        }

        final List<Double> probabilities = calculatePatchProbabilities(mutations, flakiness);
        return 1 - probabilities.stream().reduce((n1, n2) -> n1 * n2).orElse(0.0);
    }

    private static List<Double> calculatePatchProbabilities(List<Mutation> mutations, double flakiness) {
        List<Double> p = new ArrayList<>(mutations.size());

        for(Mutation mutation: mutations){
            p.add(calculatePatchProbability(mutation.getNumberCoveringTest(), flakiness));
        }

        return p;
    }

    private static double calculatePatchProbability(int numberTests, double flakiness) {
        return 1 - Math.pow((1 - flakiness), numberTests);
    }
}
