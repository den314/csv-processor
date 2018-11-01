package csv;

public class Data implements CSVWritable {

    private String name;
    private String surname;
    private Integer id;
    private Integer subId;

    public Data(String name, String surname, Integer id, Integer subId) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.subId = subId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    @Override
    public String[] values() {
        return new String[]{
                name, id.toString(), surname, subId.toString()
        };
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Data{");
        sb.append("name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", id=").append(id);
        sb.append(", subId=").append(subId);
        sb.append('}');
        return sb.toString();
    }

    static class Builder {
        private String name;
        private String surname;
        private Integer id;
        private Integer subId;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withSubId(Integer subId) {
            this.subId = subId;
            return this;
        }

        public Data build() {
            return new Data(name, surname, id, subId);
        }
    }
}
