package company.userActivity;

public class UserInterface {
    Activity activity;

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void changeActivity(String string) {
        if (string.equals("1")) {
           setActivity(new Add());
           activity.run();
        } else if (string.equals("2")) {
            setActivity(new Show());
            activity.run();
        } else if (string.equals("3")) {
            setActivity(new Create());
            activity.run();
        } else if (string.equals("0")) {
            System.exit(1);
        }
    }
}
