package uz.paynet.dto;

import lombok.Data;
import lombok.NonNull;
import uz.paynet.documents.CardType;

@Data
public class IncomingParameters {

    @NonNull
    private Long amount;
    @NonNull
    private Long purchasedAmount;
    @NonNull
    private Integer agentId;
    @NonNull
    private Integer serviceId;
    private CardType cardType;
}
