package uz.paynet.handlers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uz.paynet.documents.ServiceSettings;
import uz.paynet.dto.IncomingParameters;
import uz.paynet.dto.OutResults;
import uz.paynet.repository.ServiceSettingsRepository;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
@AllArgsConstructor
public class CalculationHandler {

    private final static Mono<ServerResponse> NOT_FOUND = ServerResponse.notFound().build();

    final ServiceSettingsRepository repository;

    public Mono<ServerResponse> getCalculation (ServerRequest request) {

        // OutResults results;

        OutResults results = OutResults.builder().build();

        // 1. Getting incoming parameters
        var in = request.bodyToMono(IncomingParameters.class).block();
        if (nonNull(in)) {

            // 2. Getting setting parameters
            var settingsList = repository.findByAgentIdAndServiceIdAndStatus(in.getAgentId(), in.getServiceId(), 1)
                    .collectList().block();

            Optional<ServiceSettings> settings;
            if (!CollectionUtils.isEmpty(settingsList)) {

                // Check has card type
                if (nonNull(in.getCardType())) {
                    settings = settingsList.stream()
                            .filter(s -> s.getCardType().equals(in.getCardType()))
                            .findFirst();
                } else {
                    settings = settingsList.stream().findFirst();
                }

                if (settings.isPresent()) {
                    results = calculate(in, settings.get());
                }
            }
        }
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(results), OutResults.class)
                .switchIfEmpty(NOT_FOUND);
    }

    private OutResults calculate(IncomingParameters in, ServiceSettings settings) {
        long amount = in.getAmount();
        long purchasedAmount = in.getPurchasedAmount();

        double rewardPaynet = 0;
        double rewardAgent = 0;
        double serviceCharge = 0;
        double commissionBank = 0;
        double commissionProcessingCenter = 0;
        double cashbackClient = 0;

        if (nonNull(settings)) {

//  reward_paynet = purchased_amount * paynet_fee / 100
            if (nonNull(settings.getPaynetFee())) {
                rewardPaynet = purchasedAmount * settings.getPaynetFee() / 100;
            }

//  reward_agent = purchased_amount * agent_fee / 100 + agent_fee_fix
            if (nonNull(settings.getAgentFee())) {
                rewardAgent = purchasedAmount * settings.getAgentFee() / 100;
            }
            if (nonNull(settings.getAgentFeeFix())) {
                rewardAgent += settings.getAgentFeeFix();
            }

//  service_charge = purchased_amount * up_comission_from_client / 100 + up_comission_from_client_fix
            if (nonNull(settings.getUpCommissionFromClient())) {
                serviceCharge = purchasedAmount * settings.getUpCommissionFromClient() / 100;
            }
            if (nonNull(settings.getUpCommissionFromClientFix())) {
                serviceCharge += settings.getUpCommissionFromClientFix();
            }

//  commission_bank = purchased_amount * epos_bank_comission_fee / 100 + epos_bank_comission_fee_fix +
//  purchased_amount * provider_procent / 100 + provider_sum_fix
            if (nonNull(settings.getEposBankCommissionFee())) {
                commissionBank = purchasedAmount * settings.getEposBankCommissionFee() / 100;
            }
            if (nonNull(settings.getEposBankCommissionFeeFix())) {
                commissionBank += settings.getEposBankCommissionFeeFix();
            }
            if (nonNull(settings.getProviderPercent())) {
                commissionBank += purchasedAmount * settings.getProviderPercent() / 100;
            }
            if (nonNull(settings.getProviderSumFix())) {
                commissionBank += settings.getProviderSumFix();
            }

//  commission_processing_center = purchased_amount * epos_processing_comission_fee / 100 + epos_processing_comission_fee_fix
            if (nonNull(settings.getEposProcessingComFee())) {
                commissionProcessingCenter = purchasedAmount * settings.getEposProcessingComFee() / 100;
            }
            if (nonNull(settings.getEposProcessingComFeeFix())) {
                commissionProcessingCenter += settings.getEposProcessingComFeeFix();
            }

//  cashback_client = amount * cashback_mobile / 100 + cashback_mobile_fix
            if (nonNull(settings.getCashbackMobile())) {
                cashbackClient = amount * settings.getCashbackMobile() / 100;
            }
            if (nonNull(settings.getCashbackMobileFix())) {
                cashbackClient += settings.getCashbackMobileFix();
            }
        }

        return OutResults.builder()
                .rewardPaynet(rewardPaynet)
                .rewardAgent(rewardAgent)
                .serviceCharge(serviceCharge)
                .commissionBank(commissionBank)
                .commissionProcessingCenter(commissionProcessingCenter)
                .cashbackClient(cashbackClient)
                .build();
    }
}
