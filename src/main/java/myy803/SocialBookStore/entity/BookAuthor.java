package myy803.SocialBookStore.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="bookauthor")
public class BookAuthor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "authorid")
	private int idauthor;
	
	@Column(name= "name")
	private String name;
	
	@ManyToMany
    @JoinTable
    (
        name = "book_author_book",
        joinColumns = @JoinColumn(name = "authorid"),
        inverseJoinColumns = @JoinColumn(name = "bookid")
    )
    private List<Book> books;
	
	public BookAuthor() {}
	
	public BookAuthor(int idauthor, String name) 
	{
		this.idauthor = idauthor;
		this.name = name;
	}
	
	public void setIdauthor(int idauthor) {this.idauthor = idauthor;}
	public int getIdauthor() {return idauthor;}

	
	public void setName(String name) {this.name = name;}
	public String getName() {return name;}

	
	public void setBooks(List<Book> books) {this.books = books;}
	public List<Book> getBooks() {return books;}

}
