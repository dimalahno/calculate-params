package uz.paynet.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uz.paynet.documents.ServiceSettings;
import uz.paynet.repository.ServiceSettingsRepository;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static uz.paynet.constants.ServiceSettingsConstants.SERVICE_SETTINGS_URL;

@Component
@AllArgsConstructor
public class ServiceSettingsHandler {

    private final static String AGENT_ID = "agentId";
    private final static String SERVICE_ID = "serviceId";
    private final static String STATUS = "status";
    private final static Mono<ServerResponse> NOT_FOUND = ServerResponse.notFound().build();

    final ServiceSettingsRepository repository;

    public Mono<ServerResponse> getAllServiceSettings(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findAll(), ServiceSettings.class)
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> createServiceSettings(ServerRequest request) {
        Mono<ServiceSettings> newSettings = request.bodyToMono(ServiceSettings.class);
        return newSettings.flatMap(settings -> ServerResponse.created(URI.create(SERVICE_SETTINGS_URL))
                .contentType(APPLICATION_JSON)
                .body(repository.save(settings), ServiceSettings.class));
    }

    public Mono<ServerResponse> getServiceSettingsByAgentId(ServerRequest request) {
        var agentId = Integer.valueOf(request.pathVariable(AGENT_ID));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findByAgentId(agentId), ServiceSettings.class)
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> getServiceSettingsByServiceId(ServerRequest request) {
        var serviceId = Integer.valueOf(request.pathVariable(SERVICE_ID));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findByServiceId(serviceId), ServiceSettings.class)
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> getServiceSettingsByAgentIdAndServiceId(ServerRequest request) {
        var agentId = Integer.valueOf(request.pathVariable(AGENT_ID));
        var serviceId = Integer.valueOf(request.pathVariable(SERVICE_ID));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findByAgentIdAndServiceId(agentId, serviceId), ServiceSettings.class);
    }

    public Mono<ServerResponse> getServiceSettingsByAgentIdAndServiceIdAndStatus(ServerRequest request) {
        var agentId = Integer.valueOf(request.pathVariable(AGENT_ID));
        var serviceId = Integer.valueOf(request.pathVariable(SERVICE_ID));
        var status = Integer.valueOf(request.pathVariable(STATUS));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findByAgentIdAndServiceIdAndStatus(agentId, serviceId, status), ServiceSettings.class);
    }
}
