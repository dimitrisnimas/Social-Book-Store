package myy803.SocialBookStore.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "userprofile")
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userprofile_id")
	private int userprofileid;
		
	@Column(name = "username")
	private String username;
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "address")
	private String address;

	@Column(name = "age")
	private int age;
	
	@Column(name = "phonenumber")
	private String phonenumber;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_authors",
        joinColumns = @JoinColumn(name = "userprofile_id"),
        inverseJoinColumns = @JoinColumn(name = "authorid")
    )
	private List<BookAuthor> favouriteBookAuthors;
	
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_categories",
        joinColumns = @JoinColumn(name = "userprofile_id"),
        inverseJoinColumns = @JoinColumn(name = "categoryid")
    )
    private List<BookCategory> favouriteBookCategories;
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_book_offers",
        joinColumns = @JoinColumn(name = "userprofile_id"),
        inverseJoinColumns = @JoinColumn(name = "bookid")
    )
    private List<Book> bookOffers;
	
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_requested_books",
        joinColumns = @JoinColumn(name = "userprofile_id"),
        inverseJoinColumns = @JoinColumn(name = "bookid")
    )
    private List<Book> requestedBooks;
    
	
	public UserProfile() {};
	
	public UserProfile(int userprofileid, String username, String fullname, String address, int age, String phonenumber,
			List<BookCategory> favouriteBookCategories, List<BookAuthor> favouriteBookAuthors, List<Book> bookOffers,
			List<Book> requestedBooks) 
	{
		this.userprofileid = userprofileid;
		this.username = username;
		this.fullname = fullname;
		this.address = address;
		this.age = age;
		this.phonenumber = phonenumber;
		this.favouriteBookCategories = favouriteBookCategories;
		this.favouriteBookAuthors = favouriteBookAuthors;
		this.bookOffers = bookOffers;
		this.requestedBooks = requestedBooks;
	}
	
	
	public void setUserprofile_id(int Userprofile_id) {this.userprofileid = Userprofile_id;}
	public int getUserprofile_id() {return userprofileid;}
		
	public void setUsername(String username) {this.username = username;}
	public String getUsername() {return username;}
	
	
	public void setFull_name(String fullname) {this.fullname = fullname;}
	public String getFullname() {return fullname;}
	
	
	public void setAddress(String address) {this.address = address;}
	public String getAddress() {return address;	}
	
	
	public void setAge(int age) {this.age = age;}
	public int getAge() {return age;}
	
	
	public void setPhonenumber(String phonenumber) {this.phonenumber = phonenumber;}
	public String getPhonenumber() {return phonenumber;}

	
	public void setFavouriteBookAuthors(List<BookAuthor> favouriteBookAuthors) {
		this.favouriteBookAuthors = favouriteBookAuthors;
	}
	public List<BookAuthor> getFavouriteBookAuthors() {return favouriteBookAuthors;}

	
	public void setFavouriteBookCategories(List<BookCategory> favouriteBookCategories) {
		this.favouriteBookCategories = favouriteBookCategories;
	}
	public List<BookCategory> getFavouriteBookCategories() {
		return favouriteBookCategories;
	}
	
	
	public void setBookOffers(List<Book> bookOffers) {this.bookOffers = bookOffers;}
	public List<Book> getBookOffers() {return bookOffers;}

	
	public void setRequestedBooks(List<Book> requestedBooks) {this.requestedBooks = requestedBooks;}
	public List<Book> getRequestedBooks() {return requestedBooks;}

}