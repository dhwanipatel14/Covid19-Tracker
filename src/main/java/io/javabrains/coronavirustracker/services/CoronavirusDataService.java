package io.javabrains.coronavirustracker.services;
import io.javabrains.coronavirustracker.model.LocationStatistics;
import org.apache.commons.csv.CSVFormat;
import  org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusDataService {
    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
  private List<LocationStatistics> allStatistics = new ArrayList<>();

    public List<LocationStatistics> getAllStatistics() {
        return allStatistics;
    }

    @PostConstruct
@Scheduled(cron = "* * 1 * * *") //TO schdule run for method on regular basis
    public void fetchCovid19data() throws IOException {
        List<LocationStatistics> newStatistics = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringReader csvReader = new StringReader(httpResponse.body());
    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
    for (CSVRecord record : records) {
        LocationStatistics locationStatistics = new LocationStatistics();
        locationStatistics.setState(record.get("Province/State"));
        locationStatistics.setCountry(record.get("Country/Region"));

        int latestCases = Integer.parseInt(record.get(record.size() - 1));
        int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
        locationStatistics.setTotalNewCases(latestCases);
        locationStatistics.setDiffFromPrevDay(latestCases-prevDayCases);
        newStatistics.add(locationStatistics);
        }
    this.allStatistics = newStatistics;

    }
}
