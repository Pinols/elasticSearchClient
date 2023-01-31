package com.example.microserviceone.elastic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ItemController {
    
    ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Value("${print.me}")
    String printme;

    @GetMapping(value = "/test")
    public String testaaa(){
        System.out.println("Cfg server: " + (printme.equals("Devprofile") ) );
        System.out.println("--------");

        try{
            return itemService.testOne();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
}
