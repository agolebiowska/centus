package centus;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import centus.models.Budget;
import centus.models.Expense;
import centus.services.Dashboard;

public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());

    private static Dashboard dashboard;

    @FXML
    private LineChart<String, Number> monthChart;

    @FXML
    private LineChart<String, Number> yearChart;

    @FXML
    private ListView expensesList;

    @FXML
    private Label monthBudget;

    @FXML
    private Label budgetLeft;

    @FXML
    private TextField editBudget;

    @FXML
    private TextField expenseValue;

    @FXML
    private TextField expenseDescription;

    @FXML
    private Button editBudgetButton;

    @FXML
    private Button saveBudgetButton;

    @FXML
    private Button addExpenseBtn;

    @FXML
    private Button saveExpenseBtn;

    @FXML
    public void initialize() throws IOException {
        authCheck();

        dashboard = new Dashboard();
        dashboard.refresh();
        showDashboard();
    }

    @FXML
    private void editMonthBudget(ActionEvent actionEvent) throws IOException {
        authCheck();

        monthBudget.setVisible(false);
        editBudget.setVisible(true);
        saveBudgetButton.setVisible(true);
        editBudgetButton.setVisible(false);
    }

    @FXML
    public void saveMonthBudget(ActionEvent actionEvent) throws IOException {
        authCheck();

        Integer budgetAmount = Integer.valueOf(editBudget.getText());

        if (budgetAmount <= 0) {
            return;
        }

        Budget budgetModel = Budget.
                findFirst("MONTH(created_at) = ?", this.dashboard.getCurrentMonth());

        if (budgetModel == null) {
            Budget budget = new Budget(budgetAmount);
            budget.add();
        } else {
            budgetModel.update(budgetAmount);
        }

        dashboard.refresh();
        showDashboard();
    }

    public void addExpense(ActionEvent actionEvent) throws IOException {
        authCheck();

        addExpenseBtn.setVisible(false);
        saveExpenseBtn.setVisible(true);
        expenseValue.setVisible(true);
        expenseDescription.setVisible(true);
    }

    public void saveExpense(ActionEvent actionEvent) throws IOException {
        authCheck();

        Integer value = Integer.valueOf(expenseValue.getText());
        String desc = expenseDescription.getText();

        if (value <= 0) {
            return;
        }

        Expense expense = new Expense(value, desc);
        expense.add();

        dashboard.refresh();
        showDashboard();
    }

    @FXML
    private void logOut(ActionEvent actionEvent) throws IOException {
        App.switchTo("loginform");
    }

    private static void authCheck() throws IOException {
        if (App.user == null) {
            logger.log(Level.SEVERE, "You shouldn't be here");
            App.switchTo("loginform");
            return;
        }
    }

    private void showDashboard() {
        monthChart.getData().clear();
        monthChart.getData().add(dashboard.getMonthChart());

        yearChart.getData().clear();
        yearChart.getData().add(dashboard.getYearChart());

        monthBudget.setText(String.valueOf(dashboard.getMonthBudget()));
        budgetLeft.setText(String.valueOf(dashboard.getBudgetLeft()));

        expensesList.getItems().clear();
        expensesList.getItems().addAll(dashboard.getExpenses());

        monthBudget.setVisible(true);
        editBudget.setVisible(false);
        saveBudgetButton.setVisible(false);
        editBudgetButton.setVisible(true);
        addExpenseBtn.setVisible(true);
        saveExpenseBtn.setVisible(false);
        expenseValue.setVisible(false);
        expenseDescription.setVisible(false);
    }
}
