package myy803.SocialBookStore.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import myy803.SocialBookStore.entity.UserProfile;


public interface UserProfileMapper extends JpaRepository<UserProfile, Integer> {
	public UserProfile findByUsername(String username);
	public UserProfile findByUserprofileid(int userprofileid);
	
}
