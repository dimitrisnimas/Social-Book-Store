package myy803.SocialBookStore.service;

import org.springframework.stereotype.Service;
import myy803.SocialBookStore.entity.User;


@Service
public interface UserService {
	public void saveUser(User user);
	
	/*No encoding for password*/
	public void saveUser2(User user);
	
    public boolean isUserPresent(User user);
    
    public User findByUsername(String username);
}
