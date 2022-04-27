package networklab.smartapp.auth;

import com.smartthings.sdk.smartapp.core.SmartAppDefinition;
import com.smartthings.sdk.smartapp.core.internal.handlers.ConfirmationHandler;
import com.smartthings.sdk.smartapp.core.internal.handlers.DefaultConfirmationHandler;
import com.smartthings.sdk.smartapp.core.models.ExecutionRequest;
import com.smartthings.sdk.smartapp.core.models.ExecutionResponse;
import com.smartthings.sdk.smartapp.spring.SpringSmartAppDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmartAppConfiguration {

    @Bean
    public ConfirmationHandler confirmationHandler() {
        return new DefaultConfirmationHandler() {
            @Override
            public ExecutionResponse handle(ExecutionRequest request) throws Exception {
                return super.handle(request);
            }
        };
    }

    @Bean
    public SmartAppDefinition smartAppDefinition(ApplicationContext applicationContext) {
        return SpringSmartAppDefinition.of(applicationContext);
    }
}
