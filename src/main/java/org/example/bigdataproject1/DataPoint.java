package org.example.bigdataproject1;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "values")
public class DataPoint {
    @Id
    private String id;

    @Field(" ")
    private String  index;

    @Field("Col-14")
    private Double value;

    public DataPoint() {}

    public DataPoint(String index, Double value) {
        this.index = index;
        this.value = value;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }

}
