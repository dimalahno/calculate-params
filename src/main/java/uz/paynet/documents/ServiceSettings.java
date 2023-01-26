package uz.paynet.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSettings {

    @Id
    private String id;
    @NonNull
    private Integer agentId;
    @NonNull
    private Integer serviceId;
    private Double paynetFee;
    private Integer paynetFeeFix;
    private Double agentFee;
    private Integer agentFeeFix;
    private Double upCommissionFromClient;
    private Integer upCommissionFromClientFix;
    private Double eposBankCommissionFee;
    private Integer eposBankCommissionFeeFix;
    private Double eposProcessingComFee;
    private Integer eposProcessingComFeeFix;
    private Double cashbackMobile;
    private Integer cashbackMobileFix;
    private Double providerPercent;
    private Integer providerSumFix;
    private Date startDate;
    private Date endDate;
    private CardType cardType;
    private Integer status;
}
