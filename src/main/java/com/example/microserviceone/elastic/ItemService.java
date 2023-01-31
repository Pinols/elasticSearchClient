package com.example.microserviceone.elastic;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;

@Service
public class ItemService {

    
    public ItemService() {
    }
    
    public String testOne() throws InterruptedException, IOException {        
        String fingerprint = "dc960407de183071fc0e737d5792a07c69bc4ce1f5964b58ce3a37907fbb200f";
        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerprint);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials("elastic", "bIBas8zvAitjDdSeSWwf")
        );

        RestClient restClient = RestClient
                .builder(new HttpHost("localhost", 9200, "https"))
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credsProv)
                )
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport( restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);
        
        //try to save (index) an item
        Item item = new Item("010101","ciao");

        IndexResponse response = client.index(i -> i
                .index("items")
                .id(item.getId())
                .document(item)
        );

        System.out.println("Indexed with version " + response.version());
        
        Thread.sleep(1000);
        
        // try to fetch an item
        GetResponse<Item> getResponse = client.get(g -> g
                        .index("items")
                        .id("010101"),
                Item.class
        );
        
        System.out.println("fetching..: "+ getResponse.source() );
        
        return getResponse.source().toString();
    }
}
