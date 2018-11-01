package csv;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class CsvRecord2DataConverter {

    public static List<CSVData> convert(List<CSVRecord> csvRecords) {
        List<CSVData> converted = new ArrayList<>();
        List<String> values = new ArrayList<>();
        csvRecords.forEach(record -> {
            for (String v : record) {
                values.add(v);
            }
            converted.add(new Data(new ArrayList<>(values)));
            values.clear();
        });
        return converted;
    }
}
