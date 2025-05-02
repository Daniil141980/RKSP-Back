package edu.tinkoff.tinkoffbackendacademypetproject.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String url;
    private Integer port;
    private String accessKey;
    private String secretKey;
    private Boolean secure;
    private String bucket;
    private Long imageSize;
}