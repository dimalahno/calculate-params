package uz.paynet.handlers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uz.paynet.dto.IncomingParameters;
import uz.paynet.dto.OutResults;
import uz.paynet.repository.ServiceSettingsRepository;

import static java.util.Objects.nonNull;

@Component
@AllArgsConstructor
public class CalculationHandler {

    final ServiceSettingsRepository repository;

    public Mono<ServerResponse> getCalculation (ServerRequest request) {
        Mono<OutResults> results = Mono.just(calculate(request));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(results, OutResults.class);
    }

    private OutResults calculate(ServerRequest request) {
        // 1. getting incoming parameters
        var in = request.bodyToMono(IncomingParameters.class).block();
        // 2. getting service settings
        var settings = repository.findByAgentIdAndServiceIdAndStatus(in.getAgentId(), in.getServiceId(), 1).block();
        // 3. calculate results

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
