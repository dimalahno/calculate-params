package uz.paynet.dto;

import lombok.Data;

@Data
public class OutResults {

    private Double rewardPaynet;
    private Double rewardAgent;
    private Double serviceCharge;
    private Double commissionBank;
    private Double commissionProcessingCenter;
    private Double cashbackClient;
}
