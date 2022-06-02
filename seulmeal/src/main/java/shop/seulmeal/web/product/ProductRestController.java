package shop.seulmeal.web.product;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.seulmeal.service.domain.Parts;
import shop.seulmeal.service.product.ProductService;

@RestController
@RequestMapping("/product/api/*")
public class ProductRestController {
	
	int pageUnit = 5;	
	int pageSize = 5;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductRestController() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}	
	
	@PostMapping("insertParts")
	public Parts insertParts(@RequestBody Parts parts) throws Exception {
		System.out.println("재료 : "+ parts);
		
		productService.insertParts(parts);
		
		return parts;
	}
	
	@GetMapping("getPartsName/{name}")
	public Parts getPartsName(@PathVariable String name, Map<String, Object> map) throws Exception {
		map.put("name", name);
		Parts parts = productService.getParts(map);
		System.out.println(parts);
		return parts;
	}
}