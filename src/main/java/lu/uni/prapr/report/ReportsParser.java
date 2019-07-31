package lu.uni.prapr.report;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsParser {
    public static List<Project> parse(File folder) throws IOException {
        if(!folder.isDirectory()){
            throw new IOException(String.format("Was expecting a directory got '%s' instead",
                    folder.getAbsolutePath()));
        }

        String[] extensions = new String[] { "xml" };
        Map<String, Project> projects = new HashMap<>();

        for(File xml: FileUtils.listFiles(folder, extensions, false)){
            XmlMapper xmlMapper = new XmlMapper();
            String bugId = FilenameUtils.getBaseName(xml.getPath());
            String[] strings = bugId.split("-");

            if(strings.length != 2){
                continue;
            }

            String projectName = strings[0];
            Mutations mutations = xmlMapper.readValue(xml, Mutations.class);

            projects.putIfAbsent(projectName, new Project(projectName));
            projects.get(projectName).addBug(bugId, mutations);
        }

        return new ArrayList<>(projects.values());
    }
}
