import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    public static final String HTTP_TASK_1_CATS =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        final CloseableHttpClient httpClient = getBuild();

        HttpGet request = new HttpGet(HTTP_TASK_1_CATS);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            List<CatFact> catFacts = mapper.readValue(response.getEntity().getContent(),
                    mapper.getTypeFactory().constructCollectionType(List.class, CatFact.class));
            catFacts.stream().filter(x -> x.getUpvotes() == null).forEach(System.out::println);
        }
    }

    private static CloseableHttpClient getBuild() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
    }
}