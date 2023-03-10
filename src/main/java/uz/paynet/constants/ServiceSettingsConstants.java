package uz.paynet.constants;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ServiceSettingsConstants {

    String SERVICE_SETTINGS_URL = "/service_settings";
    String AGENT_ID_PARAM = "/{agentId}";
    String SERVICE_ID_PARAM = "/{serviceId}";
    String STATUS = "/{status}";
    String ID = "/{id}";

    Mono<ServerResponse> NOT_FOUND = ServerResponse.notFound().build();
}
