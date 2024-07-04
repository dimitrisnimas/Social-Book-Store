package myy803.SocialBookStore.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="book")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookid")
	private int idbook;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
		
	@Column(name="userprofile_id")
	private int userprofileid;
	
	@ManyToMany
    @JoinTable
    (
        name = "book_category_book",
        joinColumns = @JoinColumn(name = "bookid"),
        inverseJoinColumns = @JoinColumn(name = "categoryid")
    )
    private List<BookCategory> bookCategories;
	
    @ManyToMany
    @JoinTable
    (
        name = "book_author_book",
        joinColumns = @JoinColumn(name = "bookid"),
        inverseJoinColumns = @JoinColumn(name = "authorid")
    )
    private List<BookAuthor> bookAuthors;
	
    @ManyToMany(mappedBy = "bookOffers")
    private List<UserProfile> offeringUsers;
    
    @ManyToMany(mappedBy = "requestedBooks")
	private List<UserProfile> requestingUsers;

	
	public Book() {}
	
	public Book(String title, String description, int userprofileid, List<BookCategory> bookCategories,List<BookAuthor> bookAuthors,
			List<UserProfile> offeringUsers, List<UserProfile> requestingUsers) 
	{
		this.title = title;
		this.description = description;
		this.userprofileid = userprofileid;
		this.bookCategories = bookCategories;
		this.bookAuthors = bookAuthors;
		this.offeringUsers = offeringUsers;
		this.requestingUsers = requestingUsers;
	}

	
	public void setIdbook(int idbook) {this.idbook = idbook;}
	public int getIdbook() {return idbook;}

	
	public void setTitle(String title) {this.title = title;}
	public String getTitle() {return title;}

	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	
	public int getUserprofileid() {return this.userprofileid;}
	public void setUserprofileid(int userprofileid) {this.userprofileid = userprofileid;}
	
	public void setBookCategories(List<BookCategory> bookCategory) {this.bookCategories = bookCategory;}
	public List<BookCategory> getBookCategories() {return bookCategories;}

	
	public void setBookAuthors(List<BookAuthor> bookAuthors) {this.bookAuthors = bookAuthors;}
	public List<BookAuthor> getBookAuthors() {return bookAuthors;}
	
	
	public void setOfferingUsers(List<UserProfile> offeringUsers) {this.offeringUsers = offeringUsers;}
	public List<UserProfile> getOfferingUsers() {return this.offeringUsers;}
	
	
	public void setRequestingUsers(List<UserProfile> requestingUsers) {this.requestingUsers = requestingUsers;}
	public List<UserProfile> getRequestingUsers() {return requestingUsers;}
	
	
	public List<String> getBookAuthorsNames()
	{
		List<String> bookauthorsnames = new ArrayList<>();
		for (BookAuthor bookAuthor : this.bookAuthors) 
		{
			bookauthorsnames.add(bookAuthor.getName());
		}
		return bookauthorsnames;
	}
	
	public String toString() 
	{
		String str = "Book id : " + this.idbook;
		// fill later
		return str;
	}

}
