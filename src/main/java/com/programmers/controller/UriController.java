package com.programmers.controller;

import com.programmers.model.CreateRequest;
import com.programmers.model.CreateResponse;
import com.programmers.service.UriService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RequiredArgsConstructor
@Controller
@RequestMapping
public class UriController {
    private final UriService uriService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("createRequest", new CreateRequest());
        return "home";
    }

    // shortUri -> uri redirect
    @GetMapping("/{shortUri}")
    public String redirectShortUri(@PathVariable @NotBlank String shortUri) throws URISyntaxException {
        String originalUri = uriService.getOriginalUri(shortUri);
        URI redirectUri = new URI(originalUri);
        return "redirect:" + redirectUri;
    }

    // uri -> shortUri create
    @PostMapping("api")
    public String createShortUri(@ModelAttribute @Valid CreateRequest request, Model model) {
        CreateResponse createResponse = uriService.createShortUri(request.getUri());
        model.addAttribute("shortUri", createResponse.getShortUri());
        model.addAttribute("count", createResponse.getCount());
        return "result";
    }
}
