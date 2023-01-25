package uz.paynet.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import uz.paynet.handlers.ServiceSettingsHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static uz.paynet.constants.ServiceSettingsConstants.SERVICE_SETTINGS;

@Configuration
public class ServiceSettingsRouter {

    @Bean
    public RouterFunction<ServerResponse> serviceSettingsRoute(ServiceSettingsHandler handler) {
        return RouterFunctions.route(GET(SERVICE_SETTINGS).and(accept(MediaType.APPLICATION_JSON)),
                        handler::getAllServiceSettings)
                .andRoute(POST(SERVICE_SETTINGS).and(accept(MediaType.APPLICATION_JSON)),
                        handler::createServiceSettings);
    }
}
