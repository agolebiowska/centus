package centus.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.HasMany;
import org.mindrot.jbcrypt.BCrypt;
import org.javalite.activejdbc.annotations.Table;

@Table("user")
@HasMany(child = Expense.class, foreignKeyName = "user_id")
@HasMany(child = Budget.class, foreignKeyName = "user_id")
public class User extends Model {

    private String username;
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = encryptPassword(password);
    }

    public static String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static Boolean verifyPassword(String password, String passwordDB){
        return BCrypt.checkpw(password, passwordDB);
    }

    public void register() {
        this.set("username", this.username);
        this.set("password", this.password);
        this.saveIt();
    }
}
