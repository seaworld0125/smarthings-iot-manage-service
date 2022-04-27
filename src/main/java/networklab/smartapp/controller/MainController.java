package networklab.smartapp.controller;

import com.smartthings.sdk.smartapp.core.Response;
import com.smartthings.sdk.smartapp.core.SmartApp;
import com.smartthings.sdk.smartapp.core.models.ConfigurationResponseData;
import com.smartthings.sdk.smartapp.core.models.EventResponseData;
import com.smartthings.sdk.smartapp.core.models.ExecutionRequest;
import com.smartthings.sdk.smartapp.core.models.ExecutionResponse;
import com.smartthings.sdk.smartapp.core.models.UninstallResponseData;
import com.smartthings.sdk.smartapp.core.models.UpdateResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final SmartApp smartApp = SmartApp.of(smartAppDefinitionSpec ->
            smartAppDefinitionSpec
                    .install(request -> Response.ok())
                    .update(request -> Response.ok(new UpdateResponseData()))
                    .configuration(request -> Response.ok(new ConfigurationResponseData()))
                    .event(request -> Response.ok(new EventResponseData()))
                    .uninstall(request -> Response.ok(new UninstallResponseData()))
    );

    @RequestMapping("/")
    public ExecutionResponse index(@RequestBody ExecutionRequest executionRequest) {
//        System.out.println("-----------------> " + executionRequest.getConfirmationData().getConfirmationUrl());
        return smartApp.execute(executionRequest);
    }
}