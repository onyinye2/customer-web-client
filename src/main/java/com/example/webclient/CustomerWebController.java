package com.example.webclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/web/customers")
public class CustomerWebController {
    
    @Autowired private RestTemplate restTemplate;

    public String apiUrl = "http://localhost:8080/customers";

    @GetMapping
    public String getAllCustomers(Model model){

        /*
         * Use RestTemplate to retrieve all customerf FROM the API and send it to them to the
         * model to be displayed in customers.html
         */
        Customer[] custData = restTemplate.getForObject(apiUrl, Customer[].class);
        
        //HttpHeaders httpHeaders = new HttpHeaders();
        model.addAttribute("customers", custData);

        return "customers";
        //return Arrays.asList(custData);
        //return new ResponseEntity<Customer[]>(Arrays.asList(custData, HttpStatus.OK));
        
    }

    @GetMapping("/create")
    public String createCustomerForm(Model model){
        /*
         * Add a new Customer object to the model to be used in customer-form.html
         */
        Customer newCustomer = new Customer(-1L, "Insert Name");
        //Customer newCustomer = restTemplate.getForObject(apiUrl, Customer.class);
        //return newCustomer;
        model.addAttribute(new Customer(null, null));
        return "customer-form";
    }   

    @PostMapping("/saveCustomer")
    public String createCustomer(@ModelAttribute Customer c){
        /*
         * send a Post request TO the API to create a new customer then redirect to customerList
         */
        c.setBalance_due(0.0);
        c.setTotal_sales(0.0);

        restTemplate.postForEntity(apiUrl, c, Customer.class); //had an issue earlier because
        //return type was boolean... could change to Boolean.class but might mess up stage 2
        return "redirect:/web/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable long id, Model model){
        /*
         * use RestTemplate to retrieve a specific customer by ID from the API
         * and add it to the model to be used in customer-form.html
         */

        Customer editCustomer = restTemplate.getForObject("http://localhost:8080/customers/"+Long.toString(id), Customer.class);
        model.addAttribute("customer", editCustomer); //add the editCustomer under customer attribute... don;t just add it randomly
        return "customer-form";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable long id, @ModelAttribute Customer c){
        /*
         * send a PUT request to the API to update an existing customer then redirect to the customer list
         */
        //Long id = (Long) model.getAttribute("id");
        restTemplate.put("http://localhost:8080/customers/"+Long.toString(id), c.getName());

        //Customer[] custData = restTemplate.getForObject(apiUrl, Customer[].class);
        //HttpHeaders httpHeaders = new HttpHeaders();
        //return Arrays.asList(custData);
        return "redirect:/web/customers";

    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable long id){ //use path to get variable instead of model.getAttribute...
        /*
         * to send a DELETE request to the API to delete a customer. then redirect to customer list?
         */
        //Long id = (Long) model.getAttribute("id");
        restTemplate.delete("http://localhost:8080/customers/"+Long.toString(id), Customer.class);

        //Customer[] custData = restTemplate.getForObject(apiUrl, Customer[].class);
        //HttpHeaders httpHeaders = new HttpHeaders();
        //return Arrays.asList(custData);
        return "redirect:/web/customers";
    }

    @GetMapping("/makePurchaseForm")
    public String makePurchaseForm(Model model){
        
        model.addAttribute(new Transaction(null, 0l, 0.0, ""));
        return "purchase-form";
    }   

    @PostMapping("/makePurchase")
    public String makePurchase(@RequestBody Transaction t){
        
        restTemplate.postForEntity(apiUrl + "/purchase/" + Long.toString(t.getId()), t, Customer.class); //had an issue earlier because
        return "redirect:/web/customers";
    }

    @PostMapping("/makePurchaseWC")
    public String makePurchaseWC(@RequestBody Transaction t){
        
        restTemplate.postForEntity(apiUrl + "/purchasewc/" + Long.toString(t.getId()), t, Customer.class); //had an issue earlier because
        return "redirect:/web/customers";
    }

    @GetMapping("/makePaymentForm")
    public String makePaymentForm(Model model){
        
        model.addAttribute(new Transaction());
        return "payment-form";
    } 
    @PostMapping("/makePayment")
    public String makePayment(@RequestBody Transaction t){
    
        restTemplate.postForEntity(apiUrl + "/payment/" + Long.toString(t.getId()), t, Customer.class); //had an issue earlier because
        return "redirect:/web/customers";
    }


}
