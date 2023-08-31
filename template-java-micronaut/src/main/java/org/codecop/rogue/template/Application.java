package org.codecop.rogue.template;

import io.micronaut.runtime.Micronaut;

public class Application {
    public static void main(String[] args) {
        // see https://docs.micronaut.io/latest/guide/#runningServer
        Micronaut.run(Application.class, args);
    }
}
