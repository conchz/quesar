package io.quesar.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

/**
 * @author dolphineor@gmail.com
 * @version 1.0
 * @date 2017-08-04
 */
public class SimpleVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(SimpleVerticle.class);

    private final String username = "quesar";
    private final String password = "iEPSMCrw2ojJ9tSr";

    @Override
    public void start() {
        logger.info("My config looks like: " + config().encode());
        RedisOptions redisCfg = new RedisOptions().setHost("0.0.0.0").setPort(16379).setAuth("5RdXzAq1");
        RedisClient redisCli = RedisClient.create(vertx, redisCfg);

        HttpServer server = vertx.createHttpServer();

        // http://vertx.io/docs/vertx-web/java/
        // https://dzone.com/articles/secure-your-vertx
        Router router = Router.router(vertx);

        router.route().handler(RoutingContext::next);
        router.route().handler(BodyHandler.create());

        AuthProvider authProvider = new AuthProvider() {
            @Override
            public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
                resultHandler.handle(new AsyncResult<User>() {
                    @Override
                    public User result() {
                        return null;
                    }

                    @Override
                    public Throwable cause() {
                        return null;
                    }

                    @Override
                    public boolean succeeded() {
                        return username.equals(authInfo.getString("username"))
                            && password.equals(authInfo.getString("password"));
                    }

                    @Override
                    public boolean failed() {
                        return false;
                    }
                });
            }
        };
        AuthHandler authHandler = BasicAuthHandler.create(authProvider);

        router.route("/job/*").handler(authHandler);

        router.route(HttpMethod.POST, "/job/push").handler(routingContext -> {
            routingContext.response().end();
        });
        router.route(HttpMethod.GET, "/job/pop").handler(routingContext -> {
            String topic = routingContext.request().getParam("topic");
            routingContext
                .response()
                .putHeader("content-type", "application/json;charset=utf-8")
                .end("{\"status\": \"OK\"}");
        });

        server.requestHandler(router::accept).listen(5000);
    }
}
