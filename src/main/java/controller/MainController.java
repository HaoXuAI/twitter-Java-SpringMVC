package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Follow;
import model.Tweet;
import model.User;
import services.FollowService;
import services.TwitterService;
import services.UserService;

/**
 * The MainController implements all functionalities of the
 * twitter service, which includes:
 * 
 * 1. An endpoint to read the message list for the current user (as identified by their HTTP Basic 
 * authentication credentials). Include messages they have sent and messages sent by users they 
 * follow. Support a “search=” parameter that can be used to further filter messages based on 
 * keyword.
 * 2. Endpoints to get the list of people the user is following as well as the followers of the user.
 * 3. An endpoint to start following another user.
 * 4. An endpoint to unfollow another user.
 * 5. An endpoint that returns the current user's "shortest distance" to some other user. 
 * The shortest distance is defined as the number of hops needed to reach a user through
 * the users you are following (not through your followers; direction matters). For example,
 * if you follow user B, your shortest distance to B is 1. If you do not follow user B, but
 * you do follow user C who follows user B, your shortest distance to B is 2.
 * 6. An endpoint that returns a list of all users, paired with their most "popular" follower.
 * The more followers someone has, the more "popular" they are. Hint: this is possible to do 
 * with a single SQL query!
 */
@Controller
public class MainController {
	
	@Autowired
	private FollowService followService;
	@Autowired
	private UserService userService;
	@Autowired
	private TwitterService twitterService;
	
	/* get tweets of the user, as described in functionality 1 */
	@RequestMapping(path = "/tweets/{username}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Map<String, List<Tweet>>> getTweets(@PathVariable String username,
			@RequestParam(value="search", required = false) String search) {
		
		Map<String, List<Tweet>> result = new HashMap<>();
		if (search == null) {
			result = twitterService.getMessage(username);
		} else {
			result = twitterService.getMessage(username, search);
		}
		return new ResponseEntity<Map<String, List<Tweet>>>(result, HttpStatus.OK);
	}
	
	/* get following users, as described in functionality 2 */
	@RequestMapping(path = "/{username}/followed", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<String>> getFollowingUsr(@PathVariable String username) {
		List<Follow> followingUrs = this.followService.getFollowingUsers(username);
		List<String> res = new ArrayList<>();
		for (Follow follow : followingUrs) {
			User user = userService.getUserById(follow.getPerson_id());
			res.add(user.getName());
		}
		return new ResponseEntity<List<String>>(res, HttpStatus.OK);
	}
	
	/* get follower users, as described in functionality 2 */
	@RequestMapping(path = "/{username}/follower", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<String>> getFollower(@PathVariable String username) {
		List<Follow> followers = this.followService.getFollowerUsers(username);
		List<String> res = new ArrayList<>();
		for (Follow follow : followers) {
			User user = userService.getUserById(follow.getFollower_id());
			res.add(user.getName());
		}
		return new ResponseEntity<List<String>>(res, HttpStatus.OK);
	}
	
	/* follow user, as described in functionality 3 */
	@RequestMapping(path = "/{username}/follow", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> followUser(@PathVariable String username,
			@RequestBody User followedUser) {
		User user = userService.getUser(username);
		int result = followService.followUser(followedUser, user);
		if (result == 1) {
			return ResponseEntity.ok("{\"Message\": \"Success!\"}");
		} else if (result == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Cannot follow user!\"}");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Relation Exist!\"}");
		}
	}
	
	/* Unfollow user, as described in functionality 3 */
	@RequestMapping(path = "/{username}/unfollow", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> unfollowUser(@PathVariable String username,
    		@RequestBody User followedUser) {
		User user = userService.getUser(username);
        int result = followService.unfollowUser(followedUser, user);
        if (result == 1) {
            return ResponseEntity.ok("{\"Message\": \"Success!\"}");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Follow relation does not exist!\"}");
        }
    }
	
	/* get shortest distance with BFS algorithm, as described in functionality 4 */
	@RequestMapping(path = "/{username}/minPath", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<String>> getMinPath(@PathVariable String username,
			@RequestBody User srcUser) throws Exception {
		
		List<String> res = this.userService.getMinPath(srcUser, username);
		
		return new ResponseEntity<List<String>>(res, HttpStatus.OK);
	}
	
	/* get popular follower, as described in functionality 5 */
	@RequestMapping(path = "/popular", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getPopularUser() {
		List<Map<Integer, Integer>> temp = this.followService.getpopularUrs();
		Map<String, String> res = new HashMap<String, String>();
		for (Map<Integer, Integer> map : temp) {
			for (Integer key : map.keySet()) {
				String name = userService.getUserById(key).getName();
				String pop = userService.getUserById(map.get(key)).getName();
				if (!res.containsKey(name)) {
					res.put(name, pop);
				}
			}
		}
		return new ResponseEntity<Map<String, String>>(res, HttpStatus.OK);
    }
	
}
