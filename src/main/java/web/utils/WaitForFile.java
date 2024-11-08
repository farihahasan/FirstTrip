package com.firsttrip.web.utils;

import java.io.IOException;
import java.nio.file.*;

public class WaitForFile {
    public static void waitForStateFile(String filePath) throws InterruptedException, IOException {
        Path path = Paths.get(filePath);
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path createdPath = (Path) event.context();
                        if (createdPath.endsWith(path.getFileName())) {
                            System.out.println("State file found: " + filePath);
                            return;
                        }
                    }
                }
                key.reset();
            }
        }
    }
}