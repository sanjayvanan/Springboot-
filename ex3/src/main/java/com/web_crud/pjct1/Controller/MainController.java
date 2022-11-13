package com.web_crud.pjct1.Controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.web_crud.pjct1.Exception.ProductNotFoundException;
import com.web_crud.pjct1.Model.Product;
import com.web_crud.pjct1.Repository.ProductRepository;
@Controller
public class MainController {
	@Autowired
	private ProductRepository ProdRepo;
	@GetMapping("")
	public String showhomepage()
	{
		return "index";
	}
	@GetMapping("/view_product")
	public String showprod(Model model)
	{
		List<Product> listprod=(List<Product>) ProdRepo.findAll();
		model.addAttribute("listprod",listprod);
		return "product";
	}
	@GetMapping("/prod/new")
	public String showform(Model model)
	{
		model.addAttribute("prod",new Product());
		model.addAttribute("pageTitle", "Add New Product");
		return "prod_form";
	}
	@PostMapping("/prod/save")
	public String addcust(Product prod, RedirectAttributes ra)
	{
		ProdRepo.save(prod);
		ra.addFlashAttribute("message","The Product has been saved Successfully");
		return "redirect:/view_product";
	}
	@GetMapping("/prod/edit/{id}")
	public String get(@PathVariable Integer id,Model model,RedirectAttributes ra)
	{
		try
		{
		Optional<Product> prod=ProdRepo.findById(id);
		if(prod.isPresent())
		{
			model.addAttribute("prod",prod.get());
			model.addAttribute("pageTitle", "Edit Product (ID: " +id+ ")");
			return "prod_form";
		}
		throw new ProductNotFoundException("Could Not Found any Product with ID "+id);
		}
		catch(ProductNotFoundException e)
		{
			ra.addFlashAttribute("message",e.getMessage());
		}
		return "redirect:/view_product";
	}
	@GetMapping("/prod/delete/{id}")
	public String deletecust(@PathVariable Integer id,RedirectAttributes ra)
	{
		try {
		Long count=ProdRepo.countById(id);
		if(count==null || count==0)
		{
			throw new ProductNotFoundException("Could not find product with ID "+id);
		}
		ProdRepo.deleteById(id);
		ra.addFlashAttribute("message","The product with ID "+id+" has been deleted");
		}
		catch(ProductNotFoundException e)
		{
			ra.addFlashAttribute("message",e.getMessage());
		}
		return "redirect:/view_product";
	}
}
