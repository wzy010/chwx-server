package cn.net.easyinfo.entity;

public enum Sex {
    FEMALE(0, "女"), MALE(1, "男");
    private int id;
    private String value;
    private Sex(int id, String value) {
        this.id = id;
        this.value = value;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
