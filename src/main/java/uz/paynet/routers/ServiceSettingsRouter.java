package uz.paynet.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import uz.paynet.handlers.ServiceSettingsHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static uz.paynet.constants.ServiceSettingsConstants.*;

@Configuration
public class ServiceSettingsRouter {

    @Bean
    public RouterFunction<ServerResponse> serviceSettingsRoute(ServiceSettingsHandler handler) {
        return RouterFunctions.route(GET(SERVICE_SETTINGS_URL).and(accept(MediaType.APPLICATION_JSON)),
                        handler::getAllServiceSettings)
                .andRoute(GET(SERVICE_SETTINGS_URL.concat(AGENT_ID_PARAM)).and(accept(MediaType.APPLICATION_JSON)),
                        handler::getServiceSettingsByAgentId)
                .andRoute(GET(SERVICE_SETTINGS_URL.concat(SERVICE_ID_PARAM)).and(accept(MediaType.APPLICATION_JSON)),
                        handler::getServiceSettingsByServiceId)
                .andRoute(GET(SERVICE_SETTINGS_URL.concat(AGENT_ID_PARAM).concat(SERVICE_ID_PARAM)).and(accept(MediaType.APPLICATION_JSON)),
                        handler::getServiceSettingsByAgentIdAndServiceId)
                .andRoute(GET(SERVICE_SETTINGS_URL.concat(AGENT_ID_PARAM).concat(SERVICE_ID_PARAM).concat(STATUS)).and(accept(MediaType.APPLICATION_JSON)),
                        handler::getServiceSettingsByAgentIdAndServiceIdAndStatus)
                .andRoute(POST(SERVICE_SETTINGS_URL).and(accept(MediaType.APPLICATION_JSON)),
                        handler::createServiceSettings)
                .andRoute(PUT(SERVICE_SETTINGS_URL.concat(ID)).and(accept(MediaType.APPLICATION_JSON)),
                        handler::updateServiceSettings)
                .andRoute(DELETE(SERVICE_SETTINGS_URL.concat(ID)).and(accept(MediaType.APPLICATION_JSON)),
                        handler::deleteServiceSettings);
    }
}
