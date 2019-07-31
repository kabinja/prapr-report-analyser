package lu.uni.prapr.export;

import lu.uni.prapr.report.Mutation;
import lu.uni.prapr.report.Project;
import lu.uni.prapr.simulation.Simulation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
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
                "Name", "BugId", "Total", "Valid", "Genuine",
                "NumberTests", "ExpectedValid", "ExpectedGenuine",
                "AtLeastOneValid", "AtLeastOneGenuine"
        };

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))){
            for(Project project: projects){
                project.getBugs().forEach((bugId, mutations) -> {
                    try {

                        List<Mutation> valid = mutations.getMutations(Mutation.Status.SURVIVED);
                        List<Mutation> genuine = mutations.getMutations(Mutation.Status.GENUINE);

                        printer.printRecord(
                                project.getName(),
                                bugId,
                                mutations.getMutations().size(),
                                valid.size(),
                                genuine.size(),
                                mutations.getAverageNumberTests(),
                                Simulation.calculateExpectation(valid, 0.05),
                                Simulation.calculateExpectation(genuine, 0.05),
                                Simulation.calculateProbabilityAtLeastOne(valid, 0.05),
                                Simulation.calculateProbabilityAtLeastOne(genuine, 0.05)
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
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
}
