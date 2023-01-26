package entities;

import java.util.Arrays;

public class Question {
    private int idNumber;
    private String category;
    private String subcategory;
    private String title;
    private String description;
    private String [] tags;

    public Question() {
    }

    public Question(int id, String category, String subcategory, String title, String description, String[] tags) {
        this.idNumber = id;
        this.category = category;
        this.subcategory = subcategory;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    public int getId() {
        return idNumber;
    }

    public void setId(int id) {
        this.idNumber = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "\nQuestion{" +
                "\n id=" + idNumber +
                ",\n category='" + category + '\'' +
                ",\n subcategory='" + subcategory + '\'' +
                ",\n title='" + title + '\'' +
                ",\n description='" + description + '\'' +
                ",\n tags=" + Arrays.toString(tags) +
                '}';
    }
}
