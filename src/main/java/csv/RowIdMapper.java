package csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RowIdMapper {

    private static final File EXISTING_CSV_FILE = new File("csv/src/main/resources/data.sql");
    private static final File NEW_CSV_FILE = new File("csv/src/main/resources/data-modified.sql");
    private static final String[] HEADER = {"col1", "col2", "col3", "col4"};

    private static CSVFormat csvFormat = CSVFormat.newFormat(',')
            .withRecordSeparator("\r\n")
            .withIgnoreEmptyLines()
            .withFirstRecordAsHeader();

    public static void main(String[] args) throws Exception {
        cleanup();
        incrementIds(getCsvRecords());
    }

    private static void incrementIds(List<CSVRecord> records) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(NEW_CSV_FILE), csvFormat)) {
            printer.printRecord((Object[]) HEADER);
            List<CSVData> convert = CsvRecord2DataConverter.convert(records);
            modify(convert, incrementEach());
            for (CSVData data : convert) {
                printer.printRecord((Object[]) data.values());
            }
            printer.flush();
        }
    }

    private static Consumer<List<CSVData>> incrementEach() {
        return entries -> {
            int start = 2000;
            int colId = 3;
            for (CSVData entry : entries) {
                entry.set(colId, Integer.toString(++start));
            }
        };
    }

    private static List<CSVRecord> getCsvRecords() throws IOException {
        List<CSVRecord> records;
        try (CSVParser csv = CSVParser.parse(EXISTING_CSV_FILE, StandardCharsets.UTF_8, csvFormat)) {
            records = csv.getRecords();
            records.forEach(record -> System.out.println(record.get("col4")));
        }
        return records;
    }

    private static void modify(List<CSVData> convert, Consumer<List<CSVData>> func) {
        func.accept(convert);
    }

    private static void cleanup() {
        if (NEW_CSV_FILE.exists()) {
            NEW_CSV_FILE.delete();
        }
    }
}
