package myy803.SocialBookStore.service;
import java.util.List;

import org.springframework.stereotype.Service;

import myy803.SocialBookStore.entity.Book;
import myy803.SocialBookStore.formsData.BookFormData;

@Service
public interface BookService {
	
	public List<BookFormData>  findAllBooks();
	public List<Book> retrieveAllBooks();
	public Book findBookByid(int idbook);
	public BookFormData findBookFormDataByid(int idbook);
	public void saveBook(BookFormData bookFormData, int userprofile_id);
	
}