package br.com.kiss.screenmatch.main;

import br.com.kiss.screenmatch.model.Episode;
import br.com.kiss.screenmatch.model.EpisodeData;
import br.com.kiss.screenmatch.model.SeasonData;
import br.com.kiss.screenmatch.model.SerieData;
import br.com.kiss.screenmatch.service.ApiConsumer;
import br.com.kiss.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private Scanner reader = new Scanner(System.in);
    private DataConverter converter = new DataConverter();
    private ApiConsumer apiConsumer = new ApiConsumer();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void menuDisplay(){
        System.out.println("Digite o nome da série para busca");
        var serieName = reader.nextLine();
        var json = apiConsumer.dataConsumer(URL + serieName.replace(" ", "+") + API_KEY);
        SerieData data = converter.obtainData(json, SerieData.class);
        System.out.println(data);

        List<SeasonData> seasonDataList = new ArrayList<>();

        for (int i = 1; i<=data.totalSeasons(); i++){
            json = apiConsumer.dataConsumer(URL + serieName.replace(" ", "+") + "&season="+ i + API_KEY);
            SeasonData seasonData = converter.obtainData(json, SeasonData.class);
            seasonDataList.add(seasonData);
        }
        seasonDataList.forEach(s -> s.episodes()
                .forEach(e -> System.out.println(e.title())));

        List<EpisodeData> episodeDataList = seasonDataList.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episódios");
        episodeDataList.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasonDataList.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(d -> new Episode(s.number(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios?: ");
        var ano = reader.nextInt();
        reader.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodes.stream()
                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getSeason() +
                                "Episódio: " + e.getTitle() +
                                "Data Lançamento: " + e.getReleaseDate().format(formatter)
                ));
    }
}