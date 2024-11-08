package com.firsttrip.web.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    /**
     * Waits for a file to exist at the specified path.
     *
     * @param filePath            The path to the file to check.
     * @param timeoutMillis       The maximum time to wait for the file (in milliseconds).
     * @param checkIntervalMillis The interval at which to check for the file's existence (in milliseconds).
     */
    public static void waitForFileExists(Path filePath, long timeoutMillis, long checkIntervalMillis) {
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            if (Files.exists(filePath)) {
                return;
            }
            try {
                Thread.sleep(checkIntervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}