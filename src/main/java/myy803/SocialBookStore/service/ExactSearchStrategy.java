package myy803.SocialBookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myy803.SocialBookStore.entity.Book;
import myy803.SocialBookStore.formsData.SearchFormData;
import myy803.SocialBookStore.mapper.BookMapper;

@Service
public class ExactSearchStrategy extends TemplateSearchStrategy {
	
	@Autowired
	private BookMapper bookMapper;
	
	@Override
	protected List<Book> makeinitialListOfBooks(SearchFormData searchDto) {
//		System.out.println(bookMapper.findBytitle(searchDto.getTitle()));
		return bookMapper.findBytitle(searchDto.getTitle());
	}

	@Override
	protected boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book) {
//		System.out.println(book.getBookAuthorsNames());
		if(book.getBookAuthorsNames().contains(searchFormData.getAuthor().getName()))
			return true;
		return false;
	}

}
