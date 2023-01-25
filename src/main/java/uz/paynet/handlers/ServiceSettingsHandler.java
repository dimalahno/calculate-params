package uz.paynet.handlers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uz.paynet.documents.ServiceSettings;
import uz.paynet.repository.ServiceSettingsRepository;

import java.net.URI;

import static uz.paynet.constants.ServiceSettingsConstants.SERVICE_SETTINGS;

@Component
@AllArgsConstructor
public class ServiceSettingsHandler {

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    final ServiceSettingsRepository repository;

    public Mono<ServerResponse> getAllServiceSettings(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.findAll(), ServiceSettings.class);
    }

    public Mono<ServerResponse> createServiceSettings(ServerRequest request) {
        Mono<ServiceSettings> newSettings = request.bodyToMono(ServiceSettings.class);
        return newSettings.flatMap(settings -> ServerResponse.created(URI.create(SERVICE_SETTINGS))
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.save(settings), ServiceSettings.class));
    }
}
