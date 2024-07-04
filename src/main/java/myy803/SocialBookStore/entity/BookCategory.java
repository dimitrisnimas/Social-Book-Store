package myy803.SocialBookStore.entity;

import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name ="bookcategory")
public class BookCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "categoryid")
	private int categoryid; 
	
	@Column(name = "name")
	private String name; 
	
	@ManyToMany
    @JoinTable
    (
        name = "book_category_book",
        joinColumns = @JoinColumn(name = "bookid"),
        inverseJoinColumns = @JoinColumn(name = "categoryid")
    )
    private List<Book> books;
	
    
  
	public BookCategory() {}
	
	public BookCategory(int categoryid, String name) 
	{
		this.categoryid = categoryid;
		this.name = name;
	}
	
	
	public void setCategoryid(int category) {this.categoryid = category;}
	public int getCategoryid() {return this.categoryid;}

	
	public void setName(String name) {this.name = name;}
	public String getName() {return this.name;}

	
	public void setBooks(List<Book> books) {this.books = books;}
	public List<Book> getBooks() {return this.books;}
}
