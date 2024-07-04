package myy803.SocialBookStore.formsData;



import java.util.List;

import myy803.SocialBookStore.entity.BookAuthor;
import myy803.SocialBookStore.entity.BookCategory;
import myy803.SocialBookStore.entity.UserProfile;

public class BookFormData {
    
    private int idbook;
    private String title;
    private String description;
    private List<BookCategory> bookCategories;
	private List<BookAuthor> bookAuthors;
	private List<UserProfile> offeringUsers;
    private List<UserProfile> requestingUsers;
    
    public BookFormData() {}
    
    public BookFormData(int idbook, String title, String description, List<BookCategory> bookCategories, List<BookAuthor> bookAuthors,
    		List<UserProfile> offeringUsers, List<UserProfile> requestingUsers) 
    {
        this.idbook = idbook;
        this.title = title;
        this.description = description;
        this.bookCategories = bookCategories;
        this.bookAuthors = bookAuthors;
        this.offeringUsers = offeringUsers;
        this.requestingUsers = requestingUsers;
    }
    
    public int getIdbook() { return idbook; }
    public void setIdbook(int idbook) { this.idbook = idbook; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    
    public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	
    public List<BookCategory> getBookCategories() { return bookCategories; }
    public void setBookCategories(List<BookCategory> bookCategories) { this.bookCategories = bookCategories; }
    
    
    public List<BookAuthor> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(List<BookAuthor> bookAuthors) { this.bookAuthors = bookAuthors; }
    
	
	public List<UserProfile> getOfferingUsers() {return offeringUsers;}
	public void setOfferingUsers(List<UserProfile> offeringUsers) {this.offeringUsers = offeringUsers;}
	
	public void addToOfferingUsers(UserProfile userProfile) {this.offeringUsers.add(userProfile);}
	
	public void setRequestingUsers(List<UserProfile> requestingUsers) {this.requestingUsers = requestingUsers;}
	public List<UserProfile> getRequestingUsers() {return requestingUsers;}
    
	public void addToRequestingUsers(UserProfile userProfile) {this.requestingUsers.add(userProfile);}
    
    @Override
    public String toString()
    {
    	String str = "BookFormData [idbook=" + idbook + ", title=" + title
    			+ ", bookCategories=";
    	
    	for(BookCategory x : this.bookCategories)
    		str = str + "," + x.getName();
    	
        str = str + ", bookAuthors=";
        for(BookAuthor x : this.bookAuthors)
        	str = str + "," + x.getName();
        
        str = str + "]";
    	return str;
    }
    
}