package uz.paynet.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import uz.paynet.handlers.CalculationHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Component
public class CalculateRouter {


    @Bean
    public RouterFunction<ServerResponse> calculateRoute(CalculationHandler handler) {
        return RouterFunctions.route(POST("/calculate").and(accept(APPLICATION_JSON)), handler::getCalculation);
    }
}
