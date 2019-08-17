package lu.uni.prapr.export;

import lu.uni.prapr.report.Mutation;
import lu.uni.prapr.report.Mutations;
import lu.uni.prapr.report.Project;
import lu.uni.prapr.simulation.Simulation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class CsvWriter {
    private static DecimalFormat df2 = new DecimalFormat("#.00");

    public static void writeProjectsSummary(File outputFolder, List<Project> projects) throws IOException {
        FileWriter out = getFileWriter(outputFolder, "projectsSummary.csv");

        String[] headers = {"Name", "Killed", "Survived"};

        try(CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))){
            for(Project project: projects){
                printer.printRecord(
                        project.getName(),
                        project.getNumberMutantsKilled(),
                        project.getNumberMutantsSurvived()
                );
            }
        }
    }

    public static void writePatchTestCoverage(File outputFolder, List<Project> projects) throws IOException {
        FileWriter out = getFileWriter(outputFolder, "patchTestCoverage.csv");

        String[] headers = {
                "BugId",
                "Total", "Valid", "Genuine", "NumberTests",
                "ExpectedValid", "AtLeastOneValid",
                "ExpectedGenuine", "AtLeastOneGenuine"
        };

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))){
            for(Project project: projects){
                project.getBugs().forEach((bugId, mutations) -> {
                    try {

                        List<Mutation> valid = mutations.getMutations(Mutation.Status.SURVIVED);
                        List<Mutation> genuine = mutations.getMutations(Mutation.Status.GENUINE);

                        if(!valid.isEmpty() && !genuine.isEmpty()){
                            printer.printRecord(
                                    bugId,
                                    mutations.getMutations().size(),
                                    valid.size(),
                                    genuine.size(),
                                    format(mutations.getAverageNumberTests()),
                                    format(Simulation.calculateExpectation(valid, 0.05)),
                                    format(Simulation.calculateProbabilityAtLeastOne(valid, 0.05)),
                                    format(Simulation.calculateExpectation(genuine, 0.05)),
                                    format(Simulation.calculateProbabilityAtLeastOne(genuine, 0.05))
                            );
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public static void writeProjectExpectation(File outputFolder, List<Project> projects) throws IOException {
        FileWriter out = getFileWriter(outputFolder, "prapr_expected.csv");

        String[] headers = {"project", "bug_id", "flakiness", "expected_valid", "expected_genuine"};

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))){
            for(Project project: projects){
                for(Map.Entry<String, Mutations> entry : project.getBugs().entrySet())
                    for(double flakiness = 0.0; flakiness <= 1.0; flakiness += 0.01){
                        List<Mutation> valid = entry.getValue().getMutations(Mutation.Status.SURVIVED);
                        List<Mutation> genuine = entry.getValue().getMutations(Mutation.Status.GENUINE);

                        printer.printRecord(
                                project.getName(),
                                entry.getKey(),
                                format(flakiness),
                                Simulation.calculateExpectation(valid, flakiness),
                                Simulation.calculateExpectation(genuine, flakiness)
                        );
                    }
            }
        }
    }

    private static FileWriter getFileWriter(File outputFolder, String fileName) throws IOException {
        File outputFile = new File(outputFolder, fileName);

        if(outputFile.exists() && !outputFile.delete()){
            throw new IOException(String.format("Cannot clean existing output file '%s'",
                    outputFile.getAbsolutePath()));
        }

        return new FileWriter(outputFile.getAbsolutePath());
    }

    private static String getMutator(Mutation mutation){
        String[] split = mutation.getDescription().split("\\.");

        if(split.length == 0){
            return "";
        }

        return split[split.length - 1];
    }

    private static String format(double value){
        return df2.format(value);
    }
}
