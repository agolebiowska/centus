package centus.services;

import centus.App;
import centus.models.Budget;
import javafx.scene.chart.XYChart;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import centus.models.Expense;

import java.time.LocalDate;
import java.util.*;

public class Dashboard {

    private static XYChart.Series monthChart;
    private static XYChart.Series yearChart;
    private static List<Expense> expensesModels;
    private static List<String> expenses;
    private static Float monthBudget;
    private static Float budgetLeft;
    private static int currentMonth;
    private static int currentYear;

    public Dashboard() {
    }

    public void refresh() {
        LocalDate currentDate = LocalDate.now();
        this.currentMonth = currentDate.getMonthValue();
        this.currentYear = currentDate.getYear();

        setExpenses();

        calculateMonthBudget();
        calculateBudgetLeft();

        makeMonthExpensesChart();
        makeYearExpensesChart();

        prepareExpenses();
    }

    public XYChart.Series getMonthChart() {
        return this.monthChart;
    }

    public XYChart.Series getYearChart() {
        return this.yearChart;
    }

    public Integer getCurrentMonth() {
        return this.currentMonth;
    }

    public Float getMonthBudget() {
        return this.monthBudget;
    }

    public Float getBudgetLeft() {
        return this.budgetLeft;
    }

    public List<String> getExpenses() {
        return this.expenses;
    }

    private void makeMonthExpensesChart() {
        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Amount of money spent");

        for (Iterator<Expense> i = this.expensesModels.iterator(); i.hasNext();) {
            Expense item = i.next();

            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getCreatedAt());
            int day = cal.get(Calendar.DAY_OF_MONTH);

            series.getData().add(new XYChart.Data(String.valueOf(day), item.getValue()));
        }

        this.monthChart = series;
    }

    private void makeYearExpensesChart() {
        XYChart.Series<String, Float> series = new XYChart.Series();
        series.setName("Amount of money spent");

        List<Expense> expenses = App.user.
                get(Expense.class, "YEAR(created_at) = ?", this.currentYear);

        HashMap<Number, Float> byMonth = new HashMap();
        for (int i = 1; i < 13; ++i) {
            byMonth.put(i, 0.0f);
        }

        for (Iterator<Expense> i = expenses.iterator(); i.hasNext(); ) {
            Expense item = i.next();

            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getCreatedAt());
            int month = cal.get(Calendar.MONTH);

            if (byMonth.get(month) == null) {
                byMonth.put(month, 0.0f);
            }
            byMonth.put(month, byMonth.get(month) + item.getValue());
        }

        for (Map.Entry<Number, Float> entry : byMonth.entrySet()) {
            series.getData().add(new XYChart.Data(String.valueOf(entry.getKey()), entry.getValue()));
        }

        this.yearChart = series;
    }

    private void calculateMonthBudget() {
        LazyList<Model> budgetModel = App.user.
                get(Budget.class, "MONTH(created_at) = ?", this.currentMonth).
                limit(1);

        if (budgetModel.size() < 1) {
            this.monthBudget = 0.0f;
            return;
        }

        Budget budget = (Budget) budgetModel.get(0);
        if (budget == null) {
            this.monthBudget = 0.0f;
            return;
        }

        this.monthBudget = Float.valueOf(budget.getAmount());
    }

    private void setExpenses() {
        List<Expense> expenses = App.user.
                get(Expense.class, "MONTH(created_at) = ?", this.currentMonth).
                orderBy("created_at desc");

        this.expensesModels = expenses;
    }

    private void calculateBudgetLeft() {
        Float amount = this.monthBudget;
        for (Iterator<Expense> i = this.expensesModels.iterator(); i.hasNext();) {
            Expense item = i.next();

            amount -= Float.valueOf(item.getValue());
        }

        this.budgetLeft = amount;
    }

    private void prepareExpenses() {
        List<String> preparedExpenses = new ArrayList();

        for (Iterator<Expense> i = this.expensesModels.iterator(); i.hasNext();) {
            Expense item = i.next();

            preparedExpenses.add(
                    item.getCreatedAt() + " - " +
                    item.getValue() + "PLN (" +
                    item.getDescription() + ")"
            );
        }

        this.expenses = preparedExpenses;
    }
}
