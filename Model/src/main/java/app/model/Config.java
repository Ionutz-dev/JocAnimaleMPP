package app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "configs")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int row;

    @Column(nullable = false)
    private int col;

    @Column(nullable = false)
    private String animal;

    @Column(nullable = false)
    private String imageUrl;

    public Config() {
    }

    public Config(int row, int col, String animal, String imageUrl) {
        this.row = row;
        this.col = col;
        this.animal = animal;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
