package pao.Components;

public abstract class IOStream<T> {

    public static final String CsvDelimiter = ",";

    public abstract String convertEntityToCsvString();

    public abstract void convertCsvStringToEntity(String CsvString);
}
