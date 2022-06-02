package shop.seulmeal.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Follow {
	
	private int followNo;
	private String userId;
	private User followingUserId;

}
