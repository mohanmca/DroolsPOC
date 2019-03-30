package com.nikias;


import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResourceApp {

    public static Logger LOGGER = LoggerFactory.getLogger(TestResourceApp.class);

    public static void main(String[] args) {
        List<String> files = Arrays.asList("rules/sample.drl");
        List<URL> resources = files.stream().map(str -> TestResourceApp.class.getClassLoader().getResource(str)).collect(Collectors.toList());
        resources.forEach(TestResourceApp::executeRules);
    }

    public static void executeRules(URL resource) {
        try {
            LOGGER.info("Testing for file " + resource);
            File file = Paths.get(resource.toURI()).toFile();
            String content = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
