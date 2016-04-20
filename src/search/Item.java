package search;

public class Item implements DataItem {

    private Object value;

    public Item(Object value) {
        this.value = value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Integer getKey() {
        return value.hashCode();
    }

    @Override
    public void setKey(Integer key) {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return value.equals(item.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
