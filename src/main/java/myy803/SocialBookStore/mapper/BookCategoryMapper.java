package myy803.SocialBookStore.mapper;


import org.springframework.data.jpa.repository.JpaRepository;
import myy803.SocialBookStore.entity.BookCategory;


public interface BookCategoryMapper extends JpaRepository<BookCategory, Integer> {
//	public List<BookCategory> findByName(String name);
	
	public BookCategory findBycategoryid(int categoryid);
	
	public BookCategory findByName (String name);
	
	}

