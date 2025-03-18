import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URL;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static final String NASA_URL = "https://api.nasa.gov/planetary/apod?api_key=ВАШ_КЛЮЧ";

    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();


        HttpGet request = new HttpGet(NASA_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);

        Nasa nasa = mapper.readValue(response.getEntity().getContent(), Nasa.class); //new TypeReference<>() {});

        String hdurl = nasa.getHdurl();
//        String hdurl = nasa.getUrl();

        String[] parts = hdurl.split("/");
        String fileName = parts[parts.length - 1];


        URL url = new URL(hdurl);
        Path outputPath = Path.of(fileName);

        try (InputStream is = url.openStream()) {
            Files.copy(is, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("Downloading image: " + fileName);

        httpClient.close();
        response.close();
    }
}
