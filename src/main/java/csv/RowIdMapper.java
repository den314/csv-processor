package csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class RowIdMapper {

    private static File existing = new File("csv/src/main/resources/data.sql");
    private static File newCsv = new File("csv/src/main/resources/data-modified.sql");

    public static void main(String[] args) throws Exception {
        if (cleanup(newCsv)) return;

        if (!existing.exists()) {
            System.err.println("Given CSV file does not exist");
        }

        CSVFormat csvFormat = CSVFormat.newFormat(',')
                .withQuote('"')
                .withRecordSeparator("\r\n")
                .withIgnoreEmptyLines()
                .withFirstRecordAsHeader();

        List<CSVRecord> records = null;
        Map<String, Integer> headerMap = null;
        try (CSVParser csv = CSVParser.parse(existing, StandardCharsets.UTF_8, csvFormat)) {

            records = csv.getRecords();
            System.out.println("Records count: " + csv.getRecordNumber());

            headerMap = csv.getHeaderMap();
            records.forEach(record -> System.out.println(record.get("col4")));
        }

        try (CSVPrinter printer = new CSVPrinter(new FileWriter(newCsv, true), csvFormat)) {
            printer.printRecord("col1", "col2", "col3", "col4");
            List<Data> convert = CsvRecord2DataConverter.convert(records);
            updateIds(convert, 10000);
            for (Data data : convert) {
                printer.printRecord(data.values());
            }
            printer.flush();
        }
    }

    private static void updateIds(List<Data> convert, int start) {
        for (Data data : convert) {
            data.setSubId(start++);
        }
    }

    private static boolean cleanup(File newCsv) {
        if (newCsv.exists()) {
            if (!newCsv.delete()) {
                System.err.println("Could not delete new csv file.");
                return true;
            }
        }
        return false;
    }
}
