package shop.seulmeal.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.seulmeal.service.domain.Parts;
import shop.seulmeal.service.domain.User;
import shop.seulmeal.service.user.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserService userService;

	int pageUnit = 5;	
	int pageSize = 5;
	
	public UserController() {
		System.out.println(this.getClass());
	}
	
	@GetMapping("insertUser")
	public String insertUser() throws Exception{	
		
		return "user/insertUser";
	}
	
	@PostMapping("insertUser")
	public String insertUser(@ModelAttribute("user") User user, HttpSession session) throws Exception {
		
		System.out.println("::user : "+user);
		userService.insertUser(user);
		session.setAttribute("user", user);
		
		return "redirect:/user/insertUserInformation";
	}
	
	
	  @GetMapping("insertUserInformation") 
	  public String insertUserInformation() throws Exception{
	  
	 return "user/insertUserInformation"; 
	 }
	 
	
	@PostMapping("inserUserInformation")
	public String insertUserInformation(@ModelAttribute("user") User user, Parts[] parts, HttpSession session ) throws Exception {
		userService.insertUserInformation(user);
		
		List<Parts> list= new ArrayList<Parts>();
		for(int i=0; i < parts.length; i++ ) {
			Parts parts1 = new Parts();
			parts1= parts[i];
			list.add(parts1);
		}
		
		User user2= (User)session.getAttribute("user");
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("userId", user2.getUserId());
		map.put("list", list);
		userService.insertHatesParts(map);
		
		user = userService.getUser(user2.getUserId());
		user.setParts( userService.getUserHatesParts(user.getUserId()));
		session.setAttribute("user", user);
		return "redirect:/";
	}
	
	@GetMapping("getUpdateUser/{userId}")
	public String getUser(@PathVariable("userId") String userId, Model model) throws Exception {
		
		User user = userService.getUser(userId);
		
		model.addAttribute("user", user);
		
		return "user/getUser";
	}
	
	@PostMapping("getUpdateUser")
	public String updateUser(@ModelAttribute("user") User user, Model model, HttpSession session) throws Exception {
		
		userService.updateUser(user);
		
		String sessionId=((User)session.getAttribute("user")).getUserId();
		if(sessionId.equals(user.getUserId())) {
			session.setAttribute("user", user);
		}
		return "user/getUser";	
	}
	
	@GetMapping("login")
	public String login() throws Exception {
		
		return "user/login";
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute("user") User user, HttpSession session) throws Exception {
		System.out.println("::user : "+user);
		
		User dbUser = userService.getUser(user.getUserId());
		
		if(user.getPassword().equals((dbUser.getPassword()))) {
			session.setAttribute("user", dbUser);
		}
		
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) throws Exception {
		
		session.invalidate();
		return "main";
	}
	
	
	public String naverLogin() throws Exception {
		return "main";
	}
	
	public String kakaoLogin() throws Exception {
		return "main";
	}
	
	@GetMapping("findUserId")
	public String findUserId() throws Exception {
		 
		return "user/findUserIdView";
	}
	
	@PostMapping("findUserId")
	public String findUserId(String name,String phone, String email, Model model) throws Exception {
		
		if(phone !=null) {
			User user = userService.confirmUserPhone(phone);

			if (name.equals(user.getUserName())) {
				model.addAttribute("userId", user.getUserId());

				return "user/findUserId";

			} else {
				return "<script>alert('등록된 아이디가 없습니다.');</script>";
			}
			
		} else {
			
			User user = userService.confirmUserEmail(email);
			
			if (name.equals(user.getUserName())) {
				model.addAttribute("userId", user.getUserId());

				return "user/findUserId";

			} else {
				return "<script>alert('등록된 아이디가 없습니다.');</script>";
			}
		}
		
	}
	
		
	@GetMapping("findUserPassword")
	public String findUserPassword(String userId, String phone, String email) throws Exception {
		
		if(phone !=null) {
			User user = userService.confirmUserPhone(phone);

			if (userId.equals(user.getUserId())) {
				
				return "user/resetUserPassword";

			} else {
				return "<script>alert('등록된 아이디가 없습니다.');</script>";
			}
			
		} else {
			
			User user = userService.confirmUserEmail(email);
			
			if (userId.equals(user.getUserId())) {
				
				return "user/resetUserPassword";

			} else {
				return "<script>alert('등록된 아이디가 없습니다.');</script>";
			}
		}
		
	}
	
	@GetMapping("userGradeModal")
	public String userGradeModal() throws Exception {
		
		return "userGradeModal";
	}
	
	@GetMapping("deleteUser")
	public String deleteUser() throws Exception {
		
		return "user/deleteUser";
	}
	
	@GetMapping("chargeuserPoint")
	public String chargeUserPoint() throws Exception {
		
		return "user/chargeUserPoint";
	}
	
	@GetMapping("getChargeUserPoint")
	public String getChargeUserPoint() throws Exception {
		
		return "user/getChargeUserPoint";
	}
	
	@GetMapping("listUserPoint")
	public String listUserPoint() throws Exception {
		
		return "user/listUserPoint";
	}
	
	
	
	
	

}