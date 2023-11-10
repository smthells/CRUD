package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class MainServlet extends HttpServlet {
    private static final String API_POSTS_PATH = "/api/posts";
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";
    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (!handleGetAllPosts(method, path, resp) &&
                !handleGetPostById(method, path, resp) &&
                !handleSavePost(method, path, req.getReader(), resp) &&
                !handleRemovePostById(method, path, resp)) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean handleGetAllPosts(String method, String path, HttpServletResponse resp) throws IOException {
        if (method.equals(GET_METHOD) && path.equals(API_POSTS_PATH)) {
            controller.all(resp);
            return true;
        }
        return false;
    }

    private boolean handleGetPostById(String method, String path, HttpServletResponse resp) throws IOException {
        if (method.equals(GET_METHOD) && path.matches(API_POSTS_PATH + "/\\d+")) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            controller.getById(id, resp);
            return true;
        }
        return false;
    }

    private boolean handleSavePost(String method, String path, Reader body, HttpServletResponse resp)
            throws IOException {
        if (method.equals(POST_METHOD) && path.equals(API_POSTS_PATH)) {
            controller.save(body, resp);
            return true;
        }
        return false;
    }

    private boolean handleRemovePostById(String method, String path, HttpServletResponse resp) throws IOException {
        if (method.equals(DELETE_METHOD) && path.matches(API_POSTS_PATH + "/\\d+")) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            controller.removeById(id, resp);
            return true;
        }
        return false;
    }
}

