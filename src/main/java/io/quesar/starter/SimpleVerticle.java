package io.quesar.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

/**
 * @author dolphineor@gmail.com
 * @version 1.0
 * @date 2017-08-04
 */
public class SimpleVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(SimpleVerticle.class);

    @Override
    public void start() {
        logger.info("My config looks like: " + config().encode());
        RedisOptions redisCfg = new RedisOptions().setHost("0.0.0.0").setPort(16379).setAuth("5RdXzAq1");
        RedisClient redisCli = RedisClient.create(vertx, redisCfg);

        HttpServer server = vertx.createHttpServer();

        // http://vertx.io/docs/vertx-web/java/
        // https://github.com/vert-x3/vertx-examples/blob/master/web-examples/src/main/java/io/vertx/example/web/auth/Server.java
        Router router = Router.router(vertx);

        router.route().handler(RoutingContext::next);
        router.route().handler(LoggerHandler.create());
        router.route().handler(BodyHandler.create());

        router.route(HttpMethod.POST, "/job/push").handler(routingContext -> {
            // TODO
            routingContext.response().end();
        });
        router.route(HttpMethod.GET, "/job/pop").handler(routingContext -> {
            // TODO
            String topic = routingContext.request().getParam("topic");
            routingContext.response().end();
        });

        server.requestHandler(router::accept).listen(9000);
    }
}
