package com.kidsart.customer.controller;

import com.itextpdf.text.DocumentException;
import com.kidsart.library.service.Impl.InvoiceService;
import com.kidsart.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class invoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/generate-pdf/{id}")
    public ResponseEntity<InputStreamResource> createPdf(@PathVariable("id") Long id) throws DocumentException {

        ByteArrayInputStream pdf=invoiceService.createPdf(id);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Content-Disposition","inline;file=lcwd.pdf");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
