package kz.zateyev.pdformation.entity;

public class Tag {
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tag) {
            Tag anotherTag = (Tag)obj;
            return this.name.equals(anotherTag.getName());
        }
        return false;
    }
}
