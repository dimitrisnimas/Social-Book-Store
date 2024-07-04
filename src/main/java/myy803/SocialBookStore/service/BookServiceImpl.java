package myy803.SocialBookStore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myy803.SocialBookStore.entity.Book;
import myy803.SocialBookStore.formsData.BookFormData;
import myy803.SocialBookStore.formsData.UserProfileFormData;
import myy803.SocialBookStore.mapper.BookMapper;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private UserProfileService userProfileService;
	
	
	@Override
	public List<BookFormData> findAllBooks() 
	{
		List<BookFormData> booksFormData = new ArrayList<>();
		
		List<Book> books = bookMapper.findAll();
		if(books.isEmpty() == false) 
		{
			for (Book book : books ) 
			{
				System.out.println("authors" + book.getBookAuthors().toString());
				BookFormData formBook = new BookFormData(
						book.getIdbook(),
						book.getTitle(),
						book.getDescription(),
						book.getBookCategories(),
						book.getBookAuthors(),
						book.getOfferingUsers(),
						book.getRequestingUsers());
				booksFormData.add(formBook); // list of all books
			}
		}
		else 
		{
			System.out.println("I did not find any book");
		}
		return booksFormData;
	}
	
	public List<Book> retrieveAllBooks()
	{
		return bookMapper.findAll();
	}

	@Override
	public Book findBookByid(int idbook) 
	{
		Book book = bookMapper.findByIdbook(idbook);
		return book;
	}
	
	@Override
	public BookFormData findBookFormDataByid(int idbook)
	{
		Book book = bookMapper.findByIdbook(idbook);
		BookFormData bookForm = new BookFormData(
				book.getIdbook(),
				book.getTitle(),
				book.getDescription(),
				book.getBookCategories(),
				book.getBookAuthors(),
				book.getOfferingUsers(),
				book.getRequestingUsers());
		
		return bookForm;
	}

	@Override
	public void saveBook(BookFormData bookFormData, int userprofile_id) 
	{
		UserProfileFormData userProfileForm = userProfileService.retreiveProfile(userprofile_id);
		
		if(userProfileForm == null)
		{
			System.err.println("BookServiceImpl : Couldn't find user profile.");
			return;
		}

		// Maybe add to new book the userProfileForm as offering user, so that the relationships are 
		// properly maintained in both directions and the join table user_book_offers is updated accordingly.
		Book book = new Book(
				bookFormData.getTitle(),
				bookFormData.getDescription(),
				userprofile_id,
				bookFormData.getBookCategories(),
				bookFormData.getBookAuthors(),
				bookFormData.getOfferingUsers(),
				bookFormData.getRequestingUsers()
				);
		
		bookMapper.save(book);
		
		// We need to update userProfile book offer list as well
		userProfileForm.addToBookOffers(book);
		userProfileService.UpdateUserProfile(userProfileForm);
	}
	
}
