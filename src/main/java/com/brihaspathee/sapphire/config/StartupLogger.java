package com.brihaspathee.sapphire.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, May 2025
 * Time: 15:21
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.config
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class StartupLogger {

    /**
     * Logs a message indicating that the Account Management Service has successfully started
     * and is ready for use. This method is triggered automatically when the application
     * is fully initialized and ready to serve requests.
     *
     * The method listens for the {@link ApplicationReadyEvent} which is published by the
     * Spring Framework when the application context has completed its startup process.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        log.info("✅ Provider Management Service is UP and ready!");
    }
}
