package br.com.kiss.screenmatch.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@ToString
public class Episode {

    private Integer season;
    private String title;
    private Integer episodeNumber;
    private double rating;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.episodeNumber();
        try {
            this.rating = Double.parseDouble(episodeData.rating());
        }catch (NumberFormatException ex){
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodeData.releaseDate());
        }catch (DateTimeParseException ex){
            this.releaseDate = null;
        }

    }
}
