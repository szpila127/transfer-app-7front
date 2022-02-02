package com.transfer.app7f.tools;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
    private static final Logger log = LoggerFactory.getLogger(Zipper.class);

    public static byte[] zip(List<FileEntry> fileEntries) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
             CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(zipOutputStream),
                     CSVFormat.DEFAULT)) {

            for (FileEntry entry : fileEntries) {
                ZipEntry zipEntry = new ZipEntry(entry.fileName);
                try {
                    zipOutputStream.putNextEntry(zipEntry);
                    for (String context : entry.getContext()) {
                        printer.printRecord(context);
                    }
                    printer.flush();
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    throw e;
                }
            }
        } catch (IOException e) {
            throw e;
        }
        return byteArrayOutputStream.toByteArray();
    }
}
