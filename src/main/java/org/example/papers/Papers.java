package org.example.papers;

import lombok.Getter;
import org.example.papers.paper.Paper;
import org.example.papers.paperReader.PaperReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Papers {
    @Getter
    private List<Paper> Papers;
    public Papers() {
        Papers = new ArrayList<>();
    }

    public void readPapers(String papersPath) {
        File folder = new File(papersPath);
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String filePath = papersPath + "\\" + file.getName();
            if (file.isFile()) {
                PaperReader myReader = new PaperReader();
                Paper p = myReader.readPaper(filePath);
                addPaper(p);
            }
        }
    }

    public void addPaper(Paper p) {
        Papers.add(p);
    }
}
