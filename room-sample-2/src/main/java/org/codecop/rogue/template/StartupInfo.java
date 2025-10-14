package org.codecop.rogue.template;

import java.util.Arrays;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;

@Singleton
public class StartupInfo implements ApplicationEventListener<StartupEvent> {

    @Value("${micronaut.server.port}")
    private int port;

    @Override
    public void onApplicationEvent(StartupEvent startupEvent) {
        System.out.println("Sample Room started on " + port + ",");
        Arrays.asList("empty", "key", "monster", "minimal", "large").stream(). //
            forEach(name -> System.out.println("Open http://localhost:" + port + "/" + name + "/"));
    }
}
