package com.kidsart.library.service.Impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.kidsart.library.dto.MonthlyCancelReportDTO;
import com.kidsart.library.model.Order;
import com.kidsart.library.model.OrderDetails;
import com.kidsart.library.service.OrderService;
import com.kidsart.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    public ByteArrayInputStream createPdf(Long id) throws DocumentException {

        Order order= orderService.findOrderById(id);

        ByteArrayOutputStream out= new ByteArrayOutputStream();
        Document document= new Document();

        PdfWriter.getInstance(document,out);
        document.open();

        Font fontTitle= FontFactory.getFont(FontFactory.HELVETICA_BOLD,25);
        Font fontHeading = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        Paragraph titlePara=new Paragraph("Order Invoice",fontTitle);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);

        Paragraph nameHeadingPara=new Paragraph("Customer name: ",fontHeading);
        Paragraph namePara=new Paragraph(order.getCustomer().getFirstName());
        document.add(nameHeadingPara);
        document.add(namePara);
        Paragraph spacingPara = new Paragraph("\n");
        document.add(spacingPara);


        Paragraph addressAndPhonePara = new Paragraph();
        addressAndPhonePara.add(new Paragraph("Address:", fontHeading));
        addressAndPhonePara.add(new Paragraph(order.getShippingAddress().getAddress_line_1()));
        addressAndPhonePara.add(new Paragraph(order.getShippingAddress().getAddress_line_2()));
        addressAndPhonePara.add(new Paragraph(order.getShippingAddress().getCity()));
        addressAndPhonePara.add(new Paragraph(order.getShippingAddress().getCountry()));
        addressAndPhonePara.add(new Paragraph(order.getShippingAddress().getPincode()));
        addressAndPhonePara.add(new Paragraph(order.getCustomer().getPhoneNumber()));
        addressAndPhonePara.add(spacingPara);
        document.add(addressAndPhonePara);

        Paragraph productAndQuantity= new Paragraph();
        productAndQuantity.add(new Paragraph("Order Details:",fontHeading));
        for(OrderDetails orderDetails: order.getOrderDetails()){
            String product="Product name : "+orderDetails.getProduct().getName();
            productAndQuantity.add(new Paragraph(product));
            String quantity="Quantity : "+orderDetails.getQuantity();
            productAndQuantity.add(new Paragraph(quantity));
        }
        document.add(productAndQuantity);

        document.add(spacingPara);
        Paragraph total=new Paragraph();
        String amount="Total : "+order.getTotalPrice();
        total.add(new Paragraph(amount));
        document.add(total);

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream createStockReport() throws DocumentException {

        List<Map<String,Object>> stockReport = productService.salesReport();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document,out);
        document.open();

        Font fontTitle= FontFactory.getFont(FontFactory.HELVETICA_BOLD,25);

        Paragraph titlePara=new Paragraph("Stock report", fontTitle);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);

        for(Map<String,Object> entry: stockReport){
            String name= (String) entry.get("productName");
            int quantity= (int) entry.get("productQuantity");
            String counts= String.valueOf(quantity);
            Paragraph reportPara=new Paragraph();
            reportPara.add(new Paragraph("Product name: "+name));
            reportPara.add(new Paragraph("Quantity :"+counts));
            document.add(reportPara);

            Paragraph spacingPara = new Paragraph("\n");
            document.add(spacingPara);
        }
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream createMonthlyCancelReport() throws DocumentException {

        List<MonthlyCancelReportDTO> cancelReport = orderService.getMonthlyCancelReport();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document,out);
        document.open();

        Font fontTitle= FontFactory.getFont(FontFactory.HELVETICA_BOLD,25);

        Paragraph titlePara=new Paragraph("Monthly cancel report",fontTitle);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);

        for (MonthlyCancelReportDTO entry : cancelReport) {
            String month = entry.getMonth();
            long count = entry.getCancelCount();
            String counts= String.valueOf(count);
            int customerId = entry.getCustomerId();


            Paragraph reportPara = new Paragraph();
            reportPara.add(new Paragraph("Month: " + month));
            reportPara.add(new Paragraph("Cancel Count: " + count));
            reportPara.add(new Paragraph("Customer ID: " + customerId));

            document.add(reportPara);

            Paragraph spacingPara = new Paragraph("\n");
            document.add(spacingPara);
        }
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

}
