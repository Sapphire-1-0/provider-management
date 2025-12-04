package com.brihaspathee.sapphire.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * The ResourceLoader instance used for locating and loading resources.
     * It acts as a central utility within the application for managing resource files.
     * This field is initialized as a final dependency, enabling usage across the class
     * for loading cypher files, handling resource patterns, and preloading data at startup.
     */
    private final ResourceLoader resourceLoader;

    /**
     * A thread-safe cache for storing cypher file contents.
     * <p>
     * This map is used to maintain a mapping between cypher file names and their
     * corresponding contents. It facilitates quick retrieval of file contents
     * without needing to repeatedly read from disk, enhancing performance and
     * reducing resource consumption. The cache is preloaded during application
     * initialization and updated dynamically when new files are requested via
     * the load methods.
     */
    private final Map<String, String> cypherCache = new ConcurrentHashMap<>();


    /**
     * Loads the content of a Cypher file from the cache or retrieves it from the file system if it is not cached.
     *
     * @param fileName the name of the Cypher file to be loaded
     * @return the content of the specified Cypher file as a string
     */
    public String load(String fileName) {
        log.info("Loading cypher file:{}", fileName);
        return cypherCache.computeIfAbsent(fileName, this::loadFromFile);
    }

    /**
     * Loads the content of a specified Cypher file from the file system.
     *
     * @param fileName the name of the Cypher file to be loaded
     * @return the content of the specified Cypher file as a string
     * @throws RuntimeException if the file is not found or cannot be loaded
     */
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

    /**
     * Preloads `.cypher` files from the classpath into an in-memory cache for faster access.
     * This method is annotated with {@code @PostConstruct}, ensuring it runs immediately after
     * the bean is initialized, typically during application startup.
     * <p>
     * The method uses a {@code PathMatchingResourcePatternResolver} to locate all `.cypher` files
     * in the specified `classpath` directory. It reads the content of each file into a string using
     * UTF-8 encoding and stores them in the {@code cypherCache} map, with the file name serving as the key.
     * <p>
     * If any I/O errors occur during file reading, a {@code RuntimeException} is thrown, indicating
     * failure to preload the files.
     * <p>
     * Logging is used to provide feedback on the progress and results of the preloading process,
     * including the successful loading of each file and the total number of files preloaded.
     */
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
