package com.company;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        User user = new User();
        ArrayList<Post> posts = new ArrayList();
        Spark.staticFileLocation("/public");
        Spark.init();

        Spark.post(
                "/create-account",
                ((request, response) -> {
                    user.name =request.queryParams("entername");
                    response.redirect("/posts");
                    return "";
                })
        );
        Spark.post(
                "create-post",
                ((request, response) -> {
                    Post post = new Post();
                    post.text = request.queryParams("post");
                    posts.add(post);
                    response.redirect("/posts");
                    return "";
                })
        );
        Spark.get(
                "/posts",
                ((request, response) -> {
                    HashMap n = new HashMap();
                    n.put("name", user.name);
                    n.put("posts", posts);
                    return new ModelAndView(n, "posts.html");
                }),
                new MustacheTemplateEngine()
        );
    }
}
