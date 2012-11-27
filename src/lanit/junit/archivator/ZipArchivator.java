package lanit.junit.archivator;

import lanit.junit.variables.GlobalVariables;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.util.Deque;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipArchivator {

    private static Logger log = Logger.getLogger(ZipArchivator.class);

    /**
     * Method adding directory content of files to ZIP archive
     * @param directory - directory for adding to ZIP archive
     * @param zipFile - result ZIP archive
     */
    public static void directoryToZip(File directory, File zipFile) {
        Closeable res = null;
        try {
            if (zipFile.exists()) {
                zipFile.delete();
            }
            URI base = directory.toURI();
            Deque<File> queue = new LinkedList<File>();
            queue.push(directory);
            OutputStream outputStream = new FileOutputStream(zipFile);
            res = outputStream;
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            res = zipOutputStream;
            while (!queue.isEmpty()) {
                directory = queue.pop();
                if (directory.getAbsolutePath().contains("screenshots")) {
                    continue;
                }
                for (File child : directory.listFiles()) {
                    String name = base.relativize(child.toURI()).getPath();
                    if (child.isDirectory()) {
                        queue.push(child);
                        name = name.endsWith("/") ? name : name + "/";
                        zipOutputStream.putNextEntry(new ZipEntry(name));
                    } else {
                        zipOutputStream.putNextEntry(new ZipEntry(name));
                        InputStream in = new FileInputStream(child);
                        try {
                            byte[] buffer = new byte[1024];
                            while (true) {
                                int readCount = in.read(buffer);
                                if (readCount < 0) {
                                    break;
                                }
                                zipOutputStream.write(buffer, 0, readCount);
                            }
                        } finally {
                            in.close();
                        }
                        zipOutputStream.closeEntry();
                    }
                }
            }
            log.info("ZIP архив " + GlobalVariables.AUTOTEST_REPORT_ZIP + " создан");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
