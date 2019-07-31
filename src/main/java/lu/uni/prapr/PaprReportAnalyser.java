package lu.uni.prapr;

import lu.uni.prapr.export.CsvWriter;
import lu.uni.prapr.report.Project;
import lu.uni.prapr.report.ReportsParser;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;

public class PaprReportAnalyser {
    public static void main(String[] args) {

        try {
            Options options = new Options();

            options.addOption("input", true, "path to folder containing xml reports");
            options.addOption("output", true, "path to output folder");

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            File input = new File(cmd.getOptionValue("input"));

            if(!input.isDirectory()){
                throw new Exception(String.format("input must be a valid directory, got '%s' instead",
                        cmd.getOptionValue("input")));
            }

            File output = new File(cmd.getOptionValue("output"));

            if(!output.isDirectory()){
                throw new Exception(String.format("output must be a valid directory, got '%s' instead",
                        cmd.getOptionValue("output")));
            }

            List<Project> projects = ReportsParser.parse(input);

            CsvWriter.writeProjectsSummary(output, projects);
            CsvWriter.writePatchTestCoverage(output, projects);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
