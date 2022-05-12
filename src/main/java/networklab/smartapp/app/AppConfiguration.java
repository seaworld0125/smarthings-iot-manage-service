package networklab.smartapp.app;

import com.smartthings.sdk.smartapp.core.extensions.ConfirmationHandler;
import com.smartthings.sdk.smartapp.core.models.AppLifecycle;
import com.smartthings.sdk.smartapp.core.models.ConfirmationData;
import java.net.URI;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.smartthings.sdk.client.ApiClient;
import com.smartthings.sdk.smartapp.core.Response;
import com.smartthings.sdk.smartapp.core.SmartAppDefinition;
import com.smartthings.sdk.smartapp.core.extensions.HttpVerificationService;
import com.smartthings.sdk.smartapp.core.extensions.InstallHandler;
import com.smartthings.sdk.smartapp.core.extensions.UninstallHandler;
import com.smartthings.sdk.smartapp.core.extensions.UpdateHandler;
import com.smartthings.sdk.smartapp.core.models.ExecutionRequest;
import com.smartthings.sdk.smartapp.core.models.ExecutionResponse;
import com.smartthings.sdk.smartapp.core.models.InstallResponseData;
import com.smartthings.sdk.smartapp.core.models.UninstallResponseData;
import com.smartthings.sdk.smartapp.core.models.UpdateResponseData;
import com.smartthings.sdk.smartapp.core.service.TokenRefreshService;
import com.smartthings.sdk.smartapp.core.service.TokenRefreshServiceImpl;
import com.smartthings.sdk.smartapp.spring.SpringSmartAppDefinition;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(AppConfiguration.class);

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }

    @Bean
    public ConfirmationHandler confirmationHandler() {
        return new ConfirmationHandler() {
            @Override
            public ExecutionResponse handle(ExecutionRequest request) throws Exception {
                LOG.debug("Confirmation Request = " + request);

                if (AppLifecycle.CONFIRMATION != request.getLifecycle()) {
                    LOG.error("Invalid lifecycle for PING handler.  lifecycle={}", request.getLifecycle());
                    throw new IllegalArgumentException("Unsupported lifecycle for ConfirmationHandler");
                }
                ConfirmationData data = request.getConfirmationData();
                URI confirmationUrl = new URI(data.getConfirmationUrl());
                RequestEntity requestEntity = new RequestEntity<>(HttpMethod.GET, confirmationUrl);

                restTemplate().exchange(requestEntity, Object.class);
                return Response.ok();
            }
        };
    }

    // In a real application (and perhaps even a future version of this
    // example), some of these simple handlers might be more complicated and it
    // might make sense to move them out into their own classes tagged with
    // @Component.
    @Bean
    public InstallHandler installHandler() {
        return executionRequest -> {
            LOG.debug("INSTALL: executionRequest = " + executionRequest);
            return Response.ok(new InstallResponseData());
        };
    }

    @Bean
    public UpdateHandler updateHandler() {
        // The update lifecycle event is called when the user updates
        // configuration options previously set via the install lifecycle
        // event so this should be similar to that handler.
        return executionRequest -> {
            LOG.debug("UPDATE: executionRequest = " + executionRequest);
            return Response.ok(new UpdateResponseData());
        };
    }

    // For simple things like uninstall, we can just implement them in-place
    // like this.
    @Bean
    public UninstallHandler uninstallHandler() {
        return executionRequest -> {
            LOG.debug("UNINSTALL: executionRequest = " + executionRequest);
            return Response.ok(new UninstallResponseData());
        };
    }

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.createDefault();
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient();
    }

    @Bean
    public HttpVerificationService httpVerificationService() {
        return new HttpVerificationService(httpClient());
    }

    @Bean
    TokenRefreshService tokenRefreshService(@Value("${smartThingsOAuthClientId}") String clientId,
            @Value("${smartThingsOAuthClientSecret}") String clientSecret) {
        return new TokenRefreshServiceImpl(clientId, clientSecret, httpClient());
    }

    @Bean
    public SmartAppDefinition smartAppDefinition(ApplicationContext applicationContext) {
        return SpringSmartAppDefinition.of(applicationContext);
    }
}