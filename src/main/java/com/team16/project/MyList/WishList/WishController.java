package com.team16.project.MyList.WishList;

import com.team16.project.core.JsonTransformer;

import static spark.Spark.post;

public class WishController {
    private static final String WISHES_API = "/WishList";
    private WishService wishService;

    public WishController() {
        wishService = new WishService();
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(WISHES_API, "application/json", (req, res) ->
        {
            return wishService.listWishes(req.body());
        }, new JsonTransformer());
    }
}
