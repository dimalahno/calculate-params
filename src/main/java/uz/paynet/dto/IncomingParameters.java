package uz.paynet.dto;

import lombok.Data;
import uz.paynet.documents.CardType;

@Data
public class IncomingParameters {

    private Long amount;
    private Long purchasedAmount;
    private String agentId;
    private String serviceId;
    private CardType cardType;
}
