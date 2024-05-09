package br.com.kiss.screenmatch;

import br.com.kiss.screenmatch.model.SerieData;
import br.com.kiss.screenmatch.service.ApiConsumer;
import br.com.kiss.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var apiConsumer = new ApiConsumer();
		var json = apiConsumer.dataConsumer("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);
		DataConverter converter = new DataConverter();
		SerieData data = converter.obtainData(json, SerieData.class);
		System.out.println(data);
	}
}
