package myy803.SocialBookStore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import myy803.SocialBookStore.entity.User;
import myy803.SocialBookStore.mapper.UserMapper;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserMapper userMapper;
		
	
	@Override
	public void saveUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userMapper.save(user);
    }
	
	@Override
	/*No encoding for password*/
	public void saveUser2(User user) {
        userMapper.save(user);
    }

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userMapper.findByUsername(user.getUsername());
		
		/*When using the Optional class, the instance itself should never be null. 
		 * An Optional is designed to either contain a value (indicating the existence of a result) or be empty, 
		 * representing the absence of a result. The Optional pattern is used in place of null to avoid NullPointerException 
		 * and make code safer and more explicit about the possibility of missing values.*/
		if(storedUser.isEmpty())
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public User findByUsername(String username) {
		Optional<User> user = userMapper.findByUsername(username);
		return user.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userMapper.findByUsername(username).orElseThrow(
	                ()-> new UsernameNotFoundException(
	                        String.format("USER_NOT_FOUND %s", username)
	                ));
	}

}
