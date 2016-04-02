package search;

public class Item implements DataItem {

    private Integer value;
    private Integer key;

    public Item(Integer value) {
        this.value = value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public void setKey(Integer key) {
        this.key = key;
    }
}
