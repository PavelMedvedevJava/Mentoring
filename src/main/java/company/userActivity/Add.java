package company.userActivity;

import company.view.DeveloperVIew;

public class Add implements Activity {
    DeveloperVIew developerVIew = new DeveloperVIew();
    @Override
    public void run() {
        developerVIew.addNewDeveloper();
    }
}
