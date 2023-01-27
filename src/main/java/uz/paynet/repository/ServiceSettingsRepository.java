package uz.paynet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import uz.paynet.documents.ServiceSettings;

@Repository
public interface ServiceSettingsRepository extends ReactiveMongoRepository<ServiceSettings, String> {

    Flux<ServiceSettings> findByAgentId(Integer agentId);

    Flux<ServiceSettings> findByServiceId(Integer serviceId);

    Flux<ServiceSettings> findByAgentIdAndServiceId(Integer agentId, Integer serviceId);

    Flux<ServiceSettings> findByAgentIdAndServiceIdAndStatus(Integer agentId, Integer serviceId, Integer status);
}
