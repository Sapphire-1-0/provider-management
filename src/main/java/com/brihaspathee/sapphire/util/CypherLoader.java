package com.brihaspathee.sapphire.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2025
 * Time: 15:28
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CypherLoader {

    private final ResourceLoader resourceLoader;
    private final Map<String, String> cypherCache = new ConcurrentHashMap<>();


    public String load(String fileName) {
        log.info("Loading cypher file:{}", fileName);
        return cypherCache.computeIfAbsent(fileName, this::loadFromFile);
    }

    public String loadFromFile(String fileName) {
        try {
            log.info("Not available in memory hence loading cypher file:{}", fileName);
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
            Resource[] resources = resolver.getResources("classpath:cypher/**/*.cypher");
            for (Resource resource : resources) {
                String resourceFileName = resource.getFilename();
                if(resourceFileName != null && resourceFileName.equals(fileName)){
                    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                }
            }
            throw new RuntimeException("Cypher file not found: " + fileName);
//            var resource = new ClassPathResource("cypher/" + fileName);
//            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }catch (IOException e){
            throw new RuntimeException("Failed to load cypher file:" + fileName, e);
        }
    }

    @PostConstruct
    public void preload(){
        try{
            var resolver = new PathMatchingResourcePatternResolver(resourceLoader);
            Resource[] resources = resolver.getResources("classpath:cypher/**/*.cypher");
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                if(fileName != null && fileName.endsWith(".cypher")){
                    byte[] bytes = resource.getInputStream().readAllBytes();
                    String content = new String(bytes, StandardCharsets.UTF_8);
                    cypherCache.put(fileName, content);
                    log.info("Loaded cypher file:{}", fileName);
                }
            }
            log.info("Preloaded {} cypher files", cypherCache.size());
        }catch (IOException e){
            throw new RuntimeException("Failed to preload cypher files", e);
        }
    }
}
