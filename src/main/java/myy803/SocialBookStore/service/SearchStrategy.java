package myy803.SocialBookStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import myy803.SocialBookStore.formsData.BookFormData;
import myy803.SocialBookStore.formsData.SearchFormData;
import myy803.SocialBookStore.mapper.BookMapper;

@Service
public interface SearchStrategy {
	public List<BookFormData> search(SearchFormData searchFormData, BookMapper bookMapper);
}
