package myy803.SocialBookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.SocialBookStore.entity.Book;
import myy803.SocialBookStore.formsData.BookFormData;
import myy803.SocialBookStore.formsData.UserProfileFormData;
import myy803.SocialBookStore.service.BookAuthorService;
import myy803.SocialBookStore.service.BookCategoryService;
import myy803.SocialBookStore.service.BookService;
import myy803.SocialBookStore.service.UserProfileService;
import myy803.SocialBookStore.service.UserService;


@Controller
public class GuestController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserProfileService userProfileService;
	@Autowired
	BookCategoryService bookCategoryService;
	@Autowired
	BookAuthorService bookAuthorService;
	@Autowired
	BookService bookService;

    @RequestMapping("/guest/dashboard")
    public String getUserHome()
    {
   	 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String currentPrincipalName = authentication.getName();
		 System.err.println("GuestController : username = " + currentPrincipalName);
		
        return "guest/dashboard";
    }
    
    
    @RequestMapping("/guest/create-profile")
    public String createProfile(Model model)
    {
    	UserProfileFormData userProfileFormData = new UserProfileFormData();
    	
    	// Give all the categories as favorite so they appear in the html form
    	userProfileFormData.setFavoriteCategories(bookCategoryService.ReturnCategories());
    	userProfileFormData.setFavoriteAuthors(bookAuthorService.ReturnAuthors());
    	
    	model.addAttribute("userProfileFormData", userProfileFormData);
    	
        return "guest/createProfile";
    }
    
    
    @RequestMapping("/guest/save-profile")
    public String saveProfile(@ModelAttribute("userProfileFormData") UserProfileFormData userProfileForm,Model model) 
    {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	
    	//save profile to base
    	userProfileForm.setUsername(username);
    	userProfileService.createUserProfile(userProfileForm);
    	
    	
    	model.addAttribute("successMessage", "Profile created, you are now a user!");
    	return "redirect:/logout";	// Note : redirecting to user dashboard is forbidden by WebSecurityConfig because of role
    }
    

    @RequestMapping("/guest/search-book")
    public String searchBook(Model theModel) 
    {
    	List<BookFormData> allTheBooks = bookService.findAllBooks();   	
    	theModel.addAttribute("books",allTheBooks);
    	    
        return "guest/searchBook";
    }
    
    @RequestMapping("/guest/seeBook")
    public String seeBook(@RequestParam("idbook") int theBookId, Model theModel) 
    {
    	Book book = bookService.findBookByid(theBookId);
    	BookFormData theBook = new BookFormData(
    			book.getIdbook(),
    			book.getTitle(),
    			book.getDescription(),
    			book.getBookCategories(),
    			book.getBookAuthors(),
    			book.getOfferingUsers(),
    			book.getRequestingUsers()
    			); 
    	theModel.addAttribute("book", theBook);
    	
    	return "guest/BookDescription";
    }
}
