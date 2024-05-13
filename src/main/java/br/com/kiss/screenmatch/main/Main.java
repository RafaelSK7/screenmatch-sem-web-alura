package br.com.kiss.screenmatch.main;

import br.com.kiss.screenmatch.model.EpisodeData;
import br.com.kiss.screenmatch.model.SeasonData;
import br.com.kiss.screenmatch.model.SerieData;
import br.com.kiss.screenmatch.service.ApiConsumer;
import br.com.kiss.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        seasonDataList.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));
    }
}