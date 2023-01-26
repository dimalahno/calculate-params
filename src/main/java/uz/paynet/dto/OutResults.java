package uz.paynet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutResults {

    private Double rewardPaynet;
    private Double rewardAgent;
    private Double serviceCharge;
    private Double commissionBank;
    private Double commissionProcessingCenter;
    private Double cashbackClient;
}
