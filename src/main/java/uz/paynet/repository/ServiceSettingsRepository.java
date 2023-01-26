package uz.paynet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.paynet.documents.CardType;
import uz.paynet.documents.ServiceSettings;

import java.util.Date;

@Repository
public interface ServiceSettingsRepository extends ReactiveMongoRepository<ServiceSettings, String> {

    Flux<ServiceSettings> findByAgentId(Integer agentId);

    Flux<ServiceSettings> findByServiceId(Integer serviceId);

    Flux<ServiceSettings> findByAgentIdAndServiceId(Integer agentId, Integer serviceId);

    Mono<ServiceSettings> findByAgentIdAndServiceIdAndStatus(Integer agentId, Integer serviceId, Integer status);

    Mono<ServiceSettings> findByAgentIdAndServiceIdAndCardTypeAndStatus(Integer agentId, Integer serviceId,
                                                                        CardType cardType, Integer status);

    Mono<ServiceSettings> findByAgentIdAndServiceIdAndStatusAndStartDateAfter(Integer agentId, Integer serviceId,
                                                                              Integer status, Date startDate);
}
