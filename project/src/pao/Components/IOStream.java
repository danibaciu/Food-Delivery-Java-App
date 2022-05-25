package pao.components;

public abstract class IOStream {

    public static final String CsvDelimiter = ",";

    public abstract String convertEntityToCsvString();

    public abstract void convertCsvStringToEntity(String CsvString);
}
