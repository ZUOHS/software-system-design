package org.example.exams.examReader;

public class ReaderFactory {
    public static AbstractReader getAbstractReader(String filePath) {
        if (filePath.endsWith(".json")) {
            return new JsonReader();
        } else if (filePath.endsWith(".xml")) {
            return new XmlReader();
        }
        return null;
    }
}
