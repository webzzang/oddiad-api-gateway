package com.exflyer.oddi.apigateway.filters;

import com.exflyer.oddi.apigateway.filters.GlobalFilter.Config;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<Config> {

  public GlobalFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      String uuid = UUID.randomUUID().toString().replaceAll("-", "");
      if (config.isPreLogger()) {
        if(!exchange.getRequest().getURI().toString().contains("swagger-ui")){
          log.info(
            "{} [REQ] {} : {} ",
            uuid,
            exchange.getRequest().getMethod(),
            exchange.getRequest().getURI());
        }
      }
      return chain
          .filter(exchange)
          .then(
              Mono.fromRunnable(
                  () -> {
                    if (config.isPostLogger()) {
                      if(!exchange.getRequest().getURI().toString().contains("swagger-ui")){
                        log.info("{}  [RES] {}", uuid, exchange.getResponse().getStatusCode());
                      }
                    }
                  }));
    };
  }

  public static class Config {
    private boolean preLogger;
    private boolean postLogger;

    public Config(boolean preLogger, boolean postLogger) {
      this.preLogger = preLogger;
      this.postLogger = postLogger;
    }

    public boolean isPreLogger() {
      return preLogger;
    }

    public boolean isPostLogger() {
      return postLogger;
    }
  }
}
