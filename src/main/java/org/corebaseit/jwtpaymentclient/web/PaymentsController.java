package org.corebaseit.jwtpaymentclient.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @GetMapping("/echo")
    public Map<String, Object> echo(Authentication auth) {
        return Map.of(
                "ok", true,
                "user", auth != null ? auth.getName() : null
        );
    }
}