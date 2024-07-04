package myy803.SocialBookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.SocialBookStore.entity.BookCategory;
import myy803.SocialBookStore.mapper.BookCategoryMapper;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

	@Autowired 
	private BookCategoryMapper bookcategoryMapper;
	
	@Override
	public List<BookCategory> ReturnCategories() {
		List<BookCategory> categories =  bookcategoryMapper.findAll();
		return  categories;
	}
	
	@Override
	public BookCategory findByName(String categoryName) {
		return bookcategoryMapper.findByName(categoryName);
	}

}
