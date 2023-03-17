package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.weg.searchsap.Caract;

import com.example.demo.utils.SapCaracUtil;

@CrossOrigin(origins = "http://localhost:4202/")
@RestController
@RequestMapping("/util/")
public class UtilController {
    @GetMapping("/sapFields")
    public List<String> findSapFields() {
        List<String> sapFields = new ArrayList<String>();
        for (Caract data: Caract.values()) {
            sapFields.add(data.name());
        }
        insertExternalFields(sapFields);
        return sapFields;
    }

    public void insertExternalFields(List<String> sapFields) {
        sapFields.add(SapCaracUtil.MAC);
        sapFields.add(SapCaracUtil.MAC_1);
        sapFields.add(SapCaracUtil.MAC_2);
        sapFields.add(SapCaracUtil.MAC_3);
        sapFields.add(SapCaracUtil.SERIAL);
        sapFields.add(SapCaracUtil.SERIAL_1);
        sapFields.add(SapCaracUtil.SERIAL_2);
        sapFields.add(SapCaracUtil.SERIAL_3);
        sapFields.add(SapCaracUtil.MATERIAL);
        sapFields.add(SapCaracUtil.ORDER);
    }
}
