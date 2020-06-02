package controller;

import model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CustomerService;
import service.CustomerServiceImpl;

import java.util.List;

@Controller
public class CustomerController {

    private CustomerService customerService= new CustomerServiceImpl();

    @GetMapping("/")
    public ModelAndView showMain(){
        ModelAndView modelAndView = new ModelAndView("index");
        List<Customer> list = customerService.findAll();
        modelAndView.addObject("list",list);
        return modelAndView;
    }

    @GetMapping("/customer/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;
    }
    @PostMapping("/customer/save")
    public String save(Customer customer, RedirectAttributes redirect) {
        customer.setId((int)(Math.random() * 10000));
        customerService.save(customer);
        redirect.addFlashAttribute("success", "Saved customer successfully!");
        return "redirect:/";
    }

    @GetMapping("/customer/{id}/edit")
    public ModelAndView showEdit(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("edit");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    @PostMapping("/customer/update")
    public String edit(@ModelAttribute Customer customer){
        customerService.update(customer.getId(),customer);
        return ("redirect:/");
    }

    @GetMapping("/customer/{id}/delete")
    public ModelAndView showDelete(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("delete");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    @PostMapping("/customer/delete")
    public String delete(@ModelAttribute Customer customer){
        customerService.remove(customer.getId());
        return ("redirect:/");
    }
}
