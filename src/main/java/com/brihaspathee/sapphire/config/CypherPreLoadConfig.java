package com.brihaspathee.sapphire.config;

import com.brihaspathee.sapphire.utils.CypherLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2025
 * Time: 15:37
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.config
 * To change this template use File | Settings | File and Code Template
 */
@Configuration
@RequiredArgsConstructor
public class CypherPreLoadConfig {

    /**
     * Handles the loading and caching of `.cypher` files for efficient retrieval during application runtime.
     * This field is a final dependency injected into the configuration class {@link CypherPreLoadConfig}.
     * <p>
     * The {@code CypherLoader} is responsible for:
     * - Preloading `.cypher` files from the classpath into an in-memory cache at application startup.
     * - Providing methods to access and efficiently read `.cypher` file contents.
     * - Reducing repetitive file I/O by maintaining a thread-safe cache of previously loaded files.
     * <p>
     * This component plays a critical role in optimizing application performance by ensuring faster access
     * to `.cypher` resources needed during operations involving the Neo4j database.
     */
    private final CypherLoader cypherLoader;

    /**
     * Preloads `.cypher` files into an in-memory cache for quick access during application runtime.
     * This method is executed immediately after the bean's initialization, as it is annotated with {@code @PostConstruct}.
     * <p>
     * The preloading process is facilitated by the {@code CypherLoader}, which reads and caches all `.cypher` files
     * available in pre-configured classpath locations. This ensures that the application can efficiently access
     * required `.cypher` content without repetitive file I/O operations after startup.
     * <p>
     * Delegates the file-loading logic to the {@code CypherLoader}'s {@code preload()} method. Logs appropriate
     * information about the preloading process to monitor the files successfully loaded into the cache.
     * <p>
     * This method is critical for optimizing performance within the application, especially for operations
     * dependent on `.cypher` content.
     */
    @PostConstruct
    public void preloadCypher() {
        cypherLoader.preload();
    }
}
