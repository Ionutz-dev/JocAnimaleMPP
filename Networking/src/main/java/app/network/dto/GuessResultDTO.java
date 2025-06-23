package app.network.dto;

public class GuessResultDTO {
    private String result;
    private String animal;
    private String imageUrl;

    public GuessResultDTO(String result, String animal, String imageUrl) {
        this.result = result;
        this.animal = animal;
        this.imageUrl = imageUrl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
