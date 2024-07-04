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
import myy803.SocialBookStore.entity.BookAuthor;
import myy803.SocialBookStore.entity.BookCategory;
import myy803.SocialBookStore.formsData.BookFormData;
import myy803.SocialBookStore.formsData.SearchFormData;
import myy803.SocialBookStore.formsData.UserProfileFormData;
import myy803.SocialBookStore.service.BookAuthorService;
import myy803.SocialBookStore.service.BookCategoryService;
import myy803.SocialBookStore.service.BookService;
import myy803.SocialBookStore.service.UserProfileService;

@Controller
public class UserController {
	
	@Autowired
	UserProfileService userProfileService;
	@Autowired
	BookCategoryService bookCategoryService;
	@Autowired
	BookAuthorService bookAuthorService;
	@Autowired
	BookService bookService;

    @RequestMapping("/user/dashboard")
    public String getUserHome(Model model)
    {
    	 //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 //String currentPrincipalName = authentication.getName();
		 //System.err.println("UserController : username=" + currentPrincipalName);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserProfileFormData userProfileForm = userProfileService.retreiveProfile(authentication.getName());
		
		model.addAttribute("user", userProfileForm);
		//System.err.println("UserController : userProfileid=" + userProfileForm.getUserprofile_id());
		return "user/dashboard";
    }
    
    
    // Get profile from base and autofill the fields in editProfile.html
    @RequestMapping("/user/edit-profile")
    public String editProfile(@RequestParam("userprofile_id") int userprofile_id,Model model)
    {
		UserProfileFormData userProfileForm = userProfileService.retreiveProfile(userprofile_id);
		
		
		model.addAttribute("userProfileForm", userProfileForm);
		model.addAttribute("categories", bookCategoryService.ReturnCategories());
		model.addAttribute("authors", bookAuthorService.ReturnAuthors());

		return "user/editProfile";
    }
    @RequestMapping("/user/save-profile")
    public String saveProfile(@RequestParam("userprofile_id") int userprofileid, @ModelAttribute("userProfileForm") UserProfileFormData userProfileForm, Model model)
    {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	
    	userProfileForm.setUsername(username);
    	userProfileService.UpdateUserProfile(userProfileForm);
    	
      	model.addAttribute("successMessage", "Update successfull!");
    	
		return "redirect:/user/dashboard";
    }
    
    

    @RequestMapping("/user/show-requests")
    public String showRequests(@RequestParam("userprofile_id") int userprofileid,Model model)
    {
    	// Get the requests for this user's books from base
    	List<BookFormData> requests = userProfileService.retrieveBookRequests(userprofileid);	
    	
    	model.addAttribute("requests", requests);
    	model.addAttribute("userprofile_id", userprofileid);
    	
        return "user/showRequests";
    }
    @RequestMapping("/user/see-requests")
    public String seeRequests(@RequestParam("bookid") int bookid,@RequestParam("userprofile_id") int userprofileid,Model model)
    {
    	BookFormData requestedBook = bookService.findBookFormDataByid(bookid);
    	
    	
    	model.addAttribute("userprofile_id", userprofileid);	// To help redirect the user to "user/ShowRequests?userprofile_id=X"
    	if(requestedBook == null)
    	{
    		model.addAttribute("successMessage", "?");
    		System.err.println("UserController : seeRequests failed");
    		return "user/seeRequests";
    	}
    	
    	model.addAttribute("bookTitle", requestedBook.getTitle());
    	model.addAttribute("users", requestedBook.getRequestingUsers());
    	
        return "user/seeRequests";
    }
    

    @RequestMapping("/user/show-recommendations")
    public String showRecommendations(@RequestParam("userprofile_id") int userprofile_id,Model theModel)
    {
    	UserProfileFormData userProfileForm = userProfileService.retreiveProfile(userprofile_id);    
        List<BookFormData> recommendedBooksCategories = userProfileService.recommendBooksByCategory(userProfileForm);
        List<BookFormData> recommendedBooksAuthors = userProfileService.recommendBooksByAuthor(userProfileForm);

        theModel.addAttribute("booksFormCategories", recommendedBooksCategories);
        theModel.addAttribute("booksAuthors", recommendedBooksAuthors);
        
        return "user/showRecommendations";
    }
    
    
    @RequestMapping("/user/my-offers-list")
    public String myBookOfferList(@RequestParam("userprofile_id") int userprofile_id, Model theModel)
    {
		List<BookFormData> bookOffers = userProfileService.retreiveBookOffers(userprofile_id);
		
		theModel.addAttribute("books", bookOffers); 
        theModel.addAttribute("userprofile_id", userprofile_id);
        
		return "/user/myOffersList";
    }
    @RequestMapping("/user/myOffersBookDetails")
    public String myOfferingsDetails(@RequestParam("idbook") int theBookId, @RequestParam("userprofile_id") int userprofile_id, Model theModel) 
    {
    	
    	Book book = bookService.findBookByid(theBookId);
    	BookFormData theBook= new BookFormData(
    			book.getIdbook(),
    			book.getTitle(),
    			book.getDescription(),
    			book.getBookCategories(),
    			book.getBookAuthors(),
    			book.getOfferingUsers(),
    			book.getRequestingUsers()
    			); 
    	
    	theModel.addAttribute("userprofile_id", userprofile_id);
    	theModel.addAttribute("book", theBook);
    	theModel.addAttribute("bookAuthors", theBook.getBookAuthors());
    	
    	return "user/myOffersBookDetails";
    }    
    @RequestMapping("/user/addBookOffer")
    public String addBookOffer(@RequestParam("userprofile_id") int userprofile_id, Model theModel)
    {
    	List<BookCategory> categories = bookCategoryService.ReturnCategories();
    	List<BookAuthor> authors = bookAuthorService.ReturnAuthors();
    	BookFormData book = new BookFormData();
    	
    	book.setBookAuthors(authors);
    	book.setBookCategories(categories);
    	
    	theModel.addAttribute("newBook",book);
        theModel.addAttribute("userprofile_id", userprofile_id);
        
		return "/user/addBookOffer";
    }
    @RequestMapping("/user/BookSave")
    public String saveBookOffer(@RequestParam("userprofile_id") int userprofile_id,@ModelAttribute("newBook") BookFormData newBook,
    		Model theModel)
    {
    	// a book must have at least 1 category and 1 author
    	if(newBook.getBookCategories() == null)
    	{
    		System.err.println("UserController: saveBookOffer(): categories are null");
    		return"redirect:/user/dashboard";
    	}
    	if(newBook.getBookAuthors() == null)
    	{
    		System.err.println("UserController: saveBookOffer(): authors are null");
    		return"redirect:/user/dashboard";
    	}
    	
    	
    	bookService.saveBook(newBook, userprofile_id);
    	
    	return"redirect:/user/dashboard";
    }
    @RequestMapping("/user/authorCreate")
    public String newAuthorCreate(@RequestParam("userprofile_id") int userprofile_id, Model theModel)
    {
    	BookAuthor bookauthor = new BookAuthor();
    	
    	theModel.addAttribute("newBookAuthor",bookauthor);
    	
    	return"/user/createAuthor";
    }
    

    
    @RequestMapping("/user/search-book")
    public String searchBook(Model theModel) 
    {
    	// Search for the requested book or see all available books? redirect to a page for this or dynamically show this to user dashboard
        List<BookFormData> allTheBooks = bookService.findAllBooks();   	
    	theModel.addAttribute("books",allTheBooks);
    	
        return "/user/searchBook";
    }
    @RequestMapping("/user/seeBook")
    public String seeBook(@RequestParam("idbook") int theBookId, Model theModel) 
    {
    	Book book = bookService.findBookByid(theBookId);
    	BookFormData theBook= new BookFormData(
    			book.getIdbook(),
    			book.getTitle(),
    			book.getDescription(),
    			book.getBookCategories(),
    			book.getBookAuthors(),
    			book.getOfferingUsers(),
    			book.getRequestingUsers()
    			);
    	
    	theModel.addAttribute("book", theBook);
    	
    	return "user/searchBookDescription";
    }
    @RequestMapping("/user/createRequest")
    public String createRequest(@RequestParam("idbook") int bookid, Model model)
    {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	BookFormData bookForm = bookService.findBookFormDataByid(bookid);	// This should not be null, because there was a button for this book
    	UserProfileFormData userProfileForm = userProfileService.retreiveProfile(username);
    	
    	model.addAttribute("book", bookForm);
    	model.addAttribute("userProfile", userProfileForm);
    	
    	
    	return "user/createBookRequest";
    }
    @RequestMapping("/user/save-book-request")
    public String saveBookRequest(@RequestParam("userprofile_id") int userprofile_id,
            @RequestParam("bookid") int bookid,
            @RequestParam("requestMessage") String requestMessage, Model model)
    {
    	
    	// Add logic to insert a request to "user_requested_books" table
    	
    	return "user/searchBook";
    }
    @RequestMapping("/user/seachBookWithStrategy")
    public String SearchStrategies(Model theModel) 
    {
    	
    	SearchFormData searchFormData = new SearchFormData();
    	theModel.addAttribute("searchObject",searchFormData);
    	
    	return "/user/searchBookWithFilters";
    }
    @RequestMapping("/user/searchBooksfilters")
    public String searchBooksFilted(@RequestParam String title,@RequestParam String author,@RequestParam String typeOfSearch,Model theModel) 
    {
    	SearchFormData searchFormData = new SearchFormData();
        searchFormData.setTitle(title);
        
        searchFormData.setAuthor(bookAuthorService.findBookAuthorByName(author,searchFormData));
        searchFormData.setTypeOfSearch(typeOfSearch);
        List<BookFormData> books = userProfileService.searchBooks(searchFormData);
        
        theModel.addAttribute("books", books);
    	
    	
    	return "/user/searchBook";
    }
    
}
