package io.axual.adapter.producer;

import io.axual.adapter.events.IFTTTEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProducerController {

    private ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/produce")
    public void produce(@RequestBody IFTTTEvent event) {
        producerService.sendMessage(event);
    }
}
