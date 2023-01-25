package uz.paynet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.paynet.documents.ServiceSettings;

@Repository
public interface ServiceSettingsRepository extends ReactiveMongoRepository<ServiceSettings, String> {

    Flux<ServiceSettings> findByAgentId(Integer agentId);

    Flux<ServiceSettings> findByServiceId(Integer serviceId);

    Mono<ServiceSettings> findByAgentIdAndServiceId(Integer agentId, Integer serviceId);
}
