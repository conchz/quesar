package io.quesar.starter;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.AbstractVerticle;
import rx.Observable;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author dolphineor@gmail.com
 * @version 1.0
 * @date 2017-08-04
 */
public class BootstrapVerticle extends AbstractVerticle {

    private static final String VERTICLES_KEY = "verticles";
    private static final String MODE_KEY = "quesar.env";
    private static final String DEFAULT_MODE = "dev";
    private final Logger logger = LoggerFactory.getLogger(BootstrapVerticle.class);

    @Override
    public void start() throws Exception {
        getConfig().getObject(VERTICLES_KEY).entrySet()
            .stream()
            .map(this::deployVerticleObservable)
            .reduce(Observable::concat)
            .orElseThrow((Supplier<Exception>) () -> new QuesarException("No available observer"))
            .subscribe(id -> logger.info("Deployment id " + id));
    }

    @SuppressWarnings("unchecked")
    private Observable<String> deployVerticleObservable(Map.Entry<String, ConfigValue> verticle) {
        String verticleName = verticle.getKey();
        JsonObject deploymentConfig = new JsonObject((Map<String, Object>) verticle.getValue().unwrapped());
        logger.info("Starting verticle " + verticleName);
        return vertx.rxDeployVerticle(verticle.getKey(), new DeploymentOptions(deploymentConfig)).toObservable();
    }

    private Config getConfig() {
        Config commonConfig = ConfigFactory.defaultApplication();
        Config envConfig = ConfigFactory.parseResourcesAnySyntax(String.format("conf/%s/application", System.getProperty(MODE_KEY, DEFAULT_MODE)));
        return envConfig.withFallback(commonConfig).resolve();
    }
}
