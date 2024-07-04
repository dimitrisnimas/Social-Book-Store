package myy803.SocialBookStore.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import myy803.SocialBookStore.entity.Book;
import myy803.SocialBookStore.entity.BookAuthor;
import myy803.SocialBookStore.entity.BookCategory;
import myy803.SocialBookStore.entity.Role;
import myy803.SocialBookStore.entity.User;
import myy803.SocialBookStore.entity.UserProfile;
import myy803.SocialBookStore.formsData.BookFormData;
import myy803.SocialBookStore.formsData.SearchFormData;
import myy803.SocialBookStore.formsData.UserProfileFormData;
import myy803.SocialBookStore.mapper.BookCategoryMapper;
import myy803.SocialBookStore.mapper.BookMapper;
import myy803.SocialBookStore.mapper.UserProfileMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private UserService userService;
	@Autowired 
	private BookMapper bookMapper;
	@Autowired 
	private BookCategoryMapper categoryMapper;
	@Autowired
	private ExactSearchStrategy exactSearch;
	@Autowired
	private ApproximateSearchStrategy approxSearch;
	
	
	@Override
	public UserProfileFormData retreiveProfile(String username) 
	{
		UserProfile userProfile = userProfileMapper.findByUsername(username);

		UserProfileFormData userProfileFormData = new UserProfileFormData(userProfile);
		
		//System.out.println("This is the userprofile object which will be returned " + userProfileFormData.toString());
		return userProfileFormData;
	}
	
	@Override 
	public UserProfileFormData retreiveProfile(int userid) 
	{
		UserProfile userProfile = userProfileMapper.findByUserprofileid(userid);		
		UserProfileFormData userProfileFormData = new UserProfileFormData(userProfile);
		return userProfileFormData;
	}

	@Override
	public void createUserProfile(UserProfileFormData userProfileFormData) 
	{
		UserProfile userProfile = new UserProfile(
				userProfileFormData.getUserprofile_id(),
				userProfileFormData.getUsername(),
				userProfileFormData.getFullname(),
				userProfileFormData.getAddress(),
				userProfileFormData.getAge(),
				userProfileFormData.getPhonenum(),
				userProfileFormData.getFavoriteCategories(),
				userProfileFormData.getFavoriteAuthors(),
				userProfileFormData.getBookOffers(),
				userProfileFormData.getRequestedBooks()
        		);
		
		
		User user = userService.findByUsername(userProfileFormData.getUsername());
		user.setRole(Role.USER);
		
		
		userService.saveUser2(user);
		userProfileMapper.save(userProfile);
	}
	
	@Override
    @Transactional
    // Update existing user profile
    public void UpdateUserProfile(UserProfileFormData userProfiledata) 
	{      
		// Checking if profile exists
        UserProfile existingProfile = userProfileMapper.findByUserprofileid(userProfiledata.getUserprofile_id());
        if(existingProfile == null)
        {
        	System.err.println("UserProfileServiceImpl : User profile not found!!");
        	return;
        }
        
        UserProfile userProfile = new UserProfile(
        		userProfiledata.getUserprofile_id(),
        		userProfiledata.getUsername(),
        		userProfiledata.getFullname(),
        		userProfiledata.getAddress(),
        		userProfiledata.getAge(),
        		userProfiledata.getPhonenum(),
        		userProfiledata.getFavoriteCategories(),
        		userProfiledata.getFavoriteAuthors(),
        		userProfiledata.getBookOffers(),
        		userProfiledata.getRequestedBooks()
        		);
        
        
        userProfileMapper.save(userProfile);
    }

	@Override
	public List<BookFormData> retrieveBookRequests(int userprofileid) 
	{
		List<BookFormData> bookRequests = new ArrayList<>();
		UserProfile userProfile = userProfileMapper.findByUserprofileid(userprofileid);
		
		if(userProfile == null)	// This "if" should never execute unless we entered wrong data straight to the database
		{
			System.err.println("UserProfileServiceImpl.retrieveBookRequests(): User profile not found for userid: " + userprofileid);
		}
		
		
		List<Book> books = bookMapper.findByUserprofileid(userprofileid);	
		
		
		for (Book book: books) 
		{
			if(!book.getRequestingUsers().isEmpty())	// Add only the books that have requests on them
			{
				System.err.println("For book " + book.getIdbook());
				for(UserProfile x : book.getRequestingUsers())
				{
					System.err.println("Requesting user : " + x.getUserprofile_id());
				}
				
				BookFormData bookFormdata = new BookFormData(
						book.getIdbook(),
						book.getTitle(),
						book.getDescription(),
						book.getBookCategories(),
						book.getBookAuthors(),
						book.getOfferingUsers(),
						book.getRequestingUsers()
						);
				
				bookRequests.add(bookFormdata);
			}
		}
		
		return bookRequests;
	}
	
	@Override
	public List<BookFormData> retreiveBookOffers(int userprofile_id) 
	{
        UserProfile userProfile = userProfileMapper.findByUserprofileid(userprofile_id);

        List<Book> bookOffers = userProfile.getBookOffers();
        
        // Convert each Book object into a corresponding BookFormData object
        List<BookFormData> bookFormDataList = new ArrayList<>();
        for (Book book : bookOffers) 
        {
            BookFormData bookFormData = new BookFormData(
            		book.getIdbook(), 
            		book.getTitle(), 
            		book.getDescription(), 
            		book.getBookCategories(),
            		book.getBookAuthors(), 
            		book.getOfferingUsers(),
            		book.getRequestingUsers()
            		);
            bookFormDataList.add(bookFormData);
        }
        return bookFormDataList;
    }
	
	@Override
	public void addBookOffer(String username, BookFormData bookFormData) 
	{
		
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		
		if(userProfile !=null) 
		{
			Book newBookOffer = new Book();
			newBookOffer.setTitle(bookFormData.getTitle());
			List<Book> bookOffers = userProfile.getBookOffers();
			bookOffers.add(newBookOffer);
			userProfileMapper.save(userProfile); // save or update 
		}else 
		{
			System.out.println("user profile not Found dor username: "+ username);
		}
	}
	
	@Override
	public void deleteBookOffer(String username, int bookid) {
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		
		if(userProfile != null) {
			List<Book> bookOffers = userProfile.getBookOffers();
			for (Iterator<Book> iterator = bookOffers.iterator(); iterator.hasNext();) 
			{
				Book book = iterator.next();
				if(book.getIdbook() == bookid) 
				{
					iterator.remove();
					break;
				}
			}
			userProfileMapper.save(userProfile);
		}else {
			System.out.println("User profile not found");
		}
	}
	
	// to return a list of books after searching
	@Override
	public List<BookFormData> searchBooks(SearchFormData searchFormData) {
		if("exact".equals(searchFormData.getTypeOfSearch()))
		{
			return exactSearch.search(searchFormData, bookMapper);
		}
		else if("approximate".equals(searchFormData.getTypeOfSearch())) {
			return approxSearch.search(searchFormData, bookMapper);
		}else {
			throw new IllegalArgumentException("Unknown search");
		}
	}

	@Override
	public List<BookFormData> recommendBooksByCategory(UserProfileFormData userProfileFormData) 
	{
		List<BookCategory> favouriteCategories = userProfileFormData.getFavoriteCategories();
		List<BookFormData> booksFormData = new ArrayList<>();
		
		for (BookCategory bookCategory : favouriteCategories) 
		{	
			
//			System.err.println("Fav categories id : " + bookCategory.getCategoryid());
			for(Book book : categoryMapper.findBycategoryid(bookCategory.getCategoryid()).getBooks())
			{
				if(book.getBookCategories().contains(bookCategory)) {
//				System.err.println("Fav categories books are :" + book.getIdbook());
				BookFormData bookFormData = new BookFormData(
						book.getIdbook(),
						book.getTitle(),
						book.getDescription(),
						book.getBookCategories(),
						book.getBookAuthors(),
						book.getOfferingUsers(),
						book.getRequestingUsers()
						);
				booksFormData.add(bookFormData);
				}
			}
		}
//		for(BookFormData book: booksFormData) {
//			System.out.println(book.toString());
//		}
		
		return booksFormData;
	}
	
	@Override
	public List<BookFormData> recommendBooksByAuthor(UserProfileFormData userProfileFormData) 
	{
		List<BookAuthor> favouriteAuthors = userProfileFormData.getFavoriteAuthors();
		List<BookFormData> booksFormData = new ArrayList<>();
		Set<Integer> addedBookIds = new HashSet<>();
		
		for (BookAuthor bookAuthor : favouriteAuthors) 
		{
			for(Book book : bookAuthor.getBooks())
			{
				for(BookAuthor x : book.getBookAuthors())
				{
					System.err.println("Book id" + book.getIdbook() + " with author " + x.getName());
				}
				if (!addedBookIds.contains(book.getIdbook())) {
					BookFormData bookFormData = new BookFormData(
							book.getIdbook(),
							book.getTitle(),
							book.getDescription(),
							book.getBookCategories(),
							book.getBookAuthors(),
							book.getOfferingUsers(),
							book.getRequestingUsers()
							);
					booksFormData.add(bookFormData);
	                addedBookIds.add(book.getIdbook());  // Add the book ID to the set
	           	}
				
			}
		}
		
		return booksFormData;
	}

	@Override
	public void requestBook(int bookid, String username) 
	{
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		
		if(userProfile != null) 
		{
			Book requestedBook = bookMapper.findByIdbook(bookid);
			if(requestedBook != null) {
				if(!userProfile.getRequestedBooks().contains(requestedBook)) {
					userProfile.getRequestedBooks().add(requestedBook);
					userProfileMapper.save(userProfile);
					
					System.out.println("Book with ID "+ requestedBook.getIdbook()+ "requested successfull");
				}else {
					System.out.println("Book with ID "+ requestedBook.getIdbook()+ "is allready requested");
				}
			}else {
				System.out.println("Book not found");
			}
		}else {
			System.out.println("User profile not found for username: " + username);
		}
	}

	@Override
	public void deleteBookRequest(String username, int bookid) {
		UserProfile userProfile = userProfileMapper.findByUsername(username);		
		
		if(userProfile!= null) 
		{
	        List<UserProfile> requestingUsers = bookMapper.findByIdbook(bookid).getRequestingUsers();
	        	
	        for (Iterator<UserProfile> iterator = requestingUsers.iterator(); iterator.hasNext();) {
	            UserProfile requestingUser = iterator.next();
	            if (requestingUser.getUserprofile_id() == userProfile.getUserprofile_id()) {
	                iterator.remove(); 
	                break; 
	            }
	        }
	        bookMapper.save(bookMapper.findByIdbook(bookid));
	    } else {
	        System.out.println("User profile not found for username: " + username);
	    }
		
	}

}
