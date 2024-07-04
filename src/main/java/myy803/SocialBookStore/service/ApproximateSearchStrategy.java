package myy803.SocialBookStore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import myy803.SocialBookStore.entity.Book;
import myy803.SocialBookStore.formsData.SearchFormData;

@Service
public class ApproximateSearchStrategy extends TemplateSearchStrategy {

	@Override
	protected List<Book> makeinitialListOfBooks(SearchFormData searchDto) {
		return bookMapper.findByTitleContaining(searchDto.getTitle());
	}

	@Override
	protected boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book) {
		return book.getBookAuthors().stream().anyMatch(author -> author.getName().contains(searchFormData.getAuthor().getName()));
			
	}
}
