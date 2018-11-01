package csv;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class CsvRecord2DataConverter {

    public static List<Data> convert(List<CSVRecord> csvRecords) {
        List<Data> converted = new ArrayList<>();
        csvRecords.forEach(record -> {

            Data data = new Data.Builder().withId(Integer.parseInt(record.get(1)))
                    .withSubId(Integer.parseInt(record.get(3)))
                    .withName(record.get(0))
                    .withSurname(record.get(2))
                    .build();

            converted.add(data);
        });
        return converted;
    }
}
