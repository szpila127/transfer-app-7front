package com.transfer.app7f.tools;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FileEntry {
    String fileName;
    List<String> context;

    public FileEntry(String fileName, List<String> context) {
        this.fileName = fileName;
        this.context = context;
    }
}
