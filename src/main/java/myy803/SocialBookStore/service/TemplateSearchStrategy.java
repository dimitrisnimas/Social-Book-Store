package myy803.SocialBookStore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.SocialBookStore.entity.Book;
import myy803.SocialBookStore.formsData.BookFormData;
import myy803.SocialBookStore.formsData.SearchFormData;
import myy803.SocialBookStore.mapper.BookMapper;

@Service
public abstract class TemplateSearchStrategy implements SearchStrategy {
	
	@Autowired 
	protected BookMapper bookMapper;

	@Override
	public List<BookFormData> search(SearchFormData searchFormData, BookMapper bookMapper) 
	{
		List<Book> initialBooks = makeinitialListOfBooks(searchFormData); //Retrieve initial list of books
		List<BookFormData> filteredBooks = new ArrayList<>(); //new list to return the books
        
		for (Book book : initialBooks) 
        {
            if (checkIfAuthorsMatch(searchFormData, book))
            {
            	BookFormData bookFormData= new BookFormData(
            			book.getIdbook(),
            			book.getTitle(),
            			book.getDescription(),
            			book.getBookCategories(),
            			book.getBookAuthors(),
            			book.getOfferingUsers(),
            			book.getRequestingUsers()
            			);
            	filteredBooks.add(bookFormData);
            }
        }
		return filteredBooks;
	}
	
	protected abstract List<Book> makeinitialListOfBooks(SearchFormData searchDto);
	
	protected abstract boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book);

	
}
