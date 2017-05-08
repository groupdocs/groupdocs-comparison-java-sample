package com.groupdocs.comparison.sample;

import com.groupdocs.comparison.common.license.License;
import com.groupdocs.comparison.sample.operations.*;
import com.groupdocs.comparison.sample.tasks.CommonIssuesTests;
import org.apache.commons.io.FileUtils;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.io.IOException;

/**
 * The type TestRunner.
 */
public class TestRunner {
    public static String PROJECT_PATH = new File("Data").getAbsolutePath();
    public static String STORAGE_PATH = PROJECT_PATH + "\\Storage";
    public static String OUTPUT_PATH = PROJECT_PATH + "\\Output";
    public static String OUTPUT_HTML_PATH = OUTPUT_PATH + "\\html";
    public static String OUTPUT_IMAGE_PATH = OUTPUT_PATH + "\\images";
    public static String LICENSE_PATH = STORAGE_PATH + "\\GroupDocs.Total.Java.lic";

    /**
     * The entry point of application.
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        applyLicense();
        cleanOutput();

        Result result = JUnitCore.runClasses(
                CommonOperationsTests.class,
                DocumentsOperationsTests.class,
                OptionsOperationsTests.class,
                OtherOperationsTests.class,
                CommonIssuesTests.class
        );

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            failure.getException().printStackTrace();
        }

        System.out.println(String.format("=== SUCCESS: %d, FAIL: %d, IGNORE: %d ===", result.getRunCount(), result.getFailureCount(), result.getIgnoreCount()));

    }

    public static void applyLicense() {
        License lic = new License();
        if (LICENSE_PATH != null && new File(LICENSE_PATH).exists()) {
            lic.setLicense(LICENSE_PATH);
        }
    }

    private static void cleanOutput() throws IOException {
        FileUtils.cleanDirectory(new File(OUTPUT_PATH));
    }

    public static String getStoragePath(String fileName, String... subDirectories) {
        StringBuilder builder = new StringBuilder(STORAGE_PATH);
        for (String part : subDirectories) {
            builder.append(File.separator).append(part);
        }
        return builder.append(File.separator).append(fileName).toString();
    }

    public static String getOutputPath(String fileName) {
        return OUTPUT_PATH + File.separator + fileName;
    }

    static {
        final File sp = new File(STORAGE_PATH);
        final File op = new File(OUTPUT_PATH);
        final File ip = new File(OUTPUT_PATH);
        final File ohp = new File(OUTPUT_HTML_PATH);
        final File oip = new File(OUTPUT_IMAGE_PATH);
        final File lcp = new File(LICENSE_PATH);
        if (!lcp.exists()) {
            LICENSE_PATH = System.getenv("GROUPDOCS_VIEWER");
            System.out.println("License file does not exists! Using license from %GROUPDOCS_VIEWER% ...");
        }
        if ((!sp.exists() && !sp.mkdirs()) || (!op.exists() && !op.mkdirs()) || (!ip.exists() && !ip.mkdirs()) || (!ohp.exists() && !ohp.mkdirs()) || (!oip.exists() && !oip.mkdirs())) {
            System.err.println("Can't create data directories!!!");
        }
    }
}