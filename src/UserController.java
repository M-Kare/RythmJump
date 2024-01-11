import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UserController {
	private User user;
	
	public UserController() {
		user = new User();
		
		init();
	}
	
	public User getRoot() {
		return user;
	}
	
	public void init() {
		user.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				System.out.println(e.getCode());
				if(e.getCode().equals(KeyCode.RIGHT)) {
					user.setX(user.getX()+5);
					System.out.println("hi");
					user.getY();
				}
			}
		});
			

	}
	
}
