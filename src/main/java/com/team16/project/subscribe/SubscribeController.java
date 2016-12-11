package com.team16.project.subscribe;

import static spark.Spark.post;

public class SubscribeController {
    private static final String SUBSCRIBE_API = "/Subscribe";
    private SubscribeService subscribeService;

    public SubscribeController() {
        subscribeService = new SubscribeService();
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(SUBSCRIBE_API, (req, res) ->
        {
            return subscribeService.subscribe(req.body());
        });
    }
}
