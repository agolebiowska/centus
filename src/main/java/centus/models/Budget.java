package centus.models;

import centus.App;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;

import java.util.Date;

@Table("budget")
@BelongsTo(parent = User.class, foreignKeyName = "user_id")
public class Budget extends Model {

    private Integer amount;
    private User user;
    private Date createdAt;

    public Budget() {}

    public Budget(Integer amount) {
        this.amount = amount;
        this.user = App.user;
    }

    public Integer getAmount() {
        return getInteger("amount");
    }

    public java.sql.Date getCreatedAt() {
        return getDate("created_at");
    }

    public void add() {
        this.set("amount", this.amount);
        this.set("user_id", this.user.getId());
        this.saveIt();
    }

    public void update(Integer amount) {
        this.set("amount", amount);
        this.saveIt();
    }
}
