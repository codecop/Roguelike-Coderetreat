package org.codecop.rogue.tester.http;

import org.codecop.rogue.tester.model.Response;

public interface Api {
    Response get(String url);

    Response post(String url);
}
