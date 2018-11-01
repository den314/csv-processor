package csv;

import java.util.*;

public class Data implements CSVData {

    private final List<String> data;

    public Data(List<String> values) {
        Objects.requireNonNull(values);
        this.data = values;
    }

    public void set(int idx, String value) {
        data.set(idx, value);
    }

    @Override
    public String[] values() {
        return data.toArray(String[]::new);
    }
}
