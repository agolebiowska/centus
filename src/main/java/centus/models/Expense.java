package centus.models;

import centus.App;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;

import java.util.Date;

@Table("expense")
@BelongsTo(parent = User.class, foreignKeyName = "user_id")
public class Expense extends Model {

    private Integer value;
    private User user;
    private String description;
    private Date createdAt;

    public Expense() {}

    public Expense(Integer value, String description) {
        this.value = value;
        this.description = description;
        this.user = App.user;
    }

    public Float getValue() {
        return Float.valueOf(getInteger("value") / 100);
    }

    public String getDescription() {
        return getString("description");
    }

    public java.sql.Date getCreatedAt() {
        return getDate("created_at");
    }

    public void add() {
        this.set("value", this.value * 100);
        this.set("description", this.description);
        this.set("user_id", this.user.getId());
        this.saveIt();
    }
}
