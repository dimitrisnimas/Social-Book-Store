package myy803.SocialBookStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import myy803.SocialBookStore.entity.BookCategory;


@Service
public interface BookCategoryService {
	public List<BookCategory>  ReturnCategories();

	public BookCategory findByName(String categoryName);
}
