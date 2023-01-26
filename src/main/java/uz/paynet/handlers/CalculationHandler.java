package uz.paynet.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uz.paynet.repository.ServiceSettingsRepository;

@Component
@AllArgsConstructor
public class CalculationHandler {

    final ServiceSettingsRepository repository;
}
